package form;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ProcessUtil;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.compiere.minigrid.IDColumn;
//import org.compiere.minigrid.IDColumn;
import org.compiere.model.MInvoice;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MPInstance;
import org.compiere.model.MProcess;
import org.compiere.model.MSequence;
import org.compiere.model.Query;
import org.compiere.model.SystemIDs;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
//import org.compiere.report.ReportStarter;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;



public class WUnprocessedInvoice implements IFormController, EventListener<Event>, WTableModelListener,ValueChangeListener{
	
	public static CLogger log = CLogger.getCLogger(WUnprocessedInvoice.class);
	
	private CustomForm form = new CustomForm();
	private WSearchEditor bpartnerSearch;
	private WTableDirEditor organizationPick;
	private Checkbox isDelivered;
	private WListbox invoiceTable;
	private WDateEditor dateDelivered;

	private WDateEditor dateDeliveredP;

	private Button selectAllButton;

//	private LabelElement unselectAllButton;

	private Button searchButton;

	private WStringEditor noteField;

	private Button processButton;

	private Label labelInfo = new Label();

	private Label dateDeliveredLabel;

	private Label dateDeliveredL;

	private Label noteLabel;

	private Vector<Vector<Object>> linesDataTableInvoice  = new Vector<Vector<Object>>();
	
	private Vector<Vector<Object>> linesDataTableInvoiceToProcess  = new Vector<Vector<Object>>();
	
	public  SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd 00:00:00");

	private Label labelInvoiceNumber;

	private Button cleanButton;
	public int AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());

	private Label deliveredNoLabel;

	private WStringEditor deliveredNoField;

//	private MSequence sequence;

	private WStringEditor DocumentNoField;

	private WListbox invoiceTableToProcess;

	private Button btnAddInvoice;

	private WTableDirEditor salesRepD;

	private Button btnDeleteInvoice;

	private ListModelTable invoiceTableModelToProcess;

	private WTableDirEditor salesRep;

	private WStringEditor deliveredNoFieldSearch;

	private Label deliveredNoFieldSearchL;

	private Label asignar;
	private MSequence sequence;

	@Override
	public ADForm getForm() {
		return form;
	}

	public WUnprocessedInvoice() {
		try {
			loadGlobalVariables();
			zkInit();


		} catch (Exception e) {
			log.log(Level.SEVERE, "", e);
		}
	}
	private void zkInit() throws Exception {

		Borderlayout mainLayout = new Borderlayout();
		form.appendChild(mainLayout);

		// ***************************NORTH****************************//
		North northLayout = new North();
		mainLayout.appendChild(northLayout);
		
		Grid filterInvoice = new Grid();
		northLayout.appendChild(filterInvoice);
		Rows rowsFilterInvoice = filterInvoice.newRows();
		Row  rowFilterInvoice = rowsFilterInvoice.newRow();

		//SalesRep
		Label salesRepLabel = new Label(Msg.translate(Env.getCtx(), "SalesRep_ID"));
		rowFilterInvoice.appendChild(salesRepLabel.rightAlign());
		MLookup lookupSR = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 59537, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_User_ID", 0, false, "(C_BPartner_ID in (select C_BPartner_ID from C_BPartner where IsSalesRep='Y'))");//MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 0, 3512, DisplayType.Search);
		salesRep = new WTableDirEditor("AD_User_ID", true, false, true, lookupSR);
		rowFilterInvoice.appendCellChild(salesRep.getComponent(), 2);

		//DocumentNo
		Label serialLabel = new Label(Msg.translate(Env.getCtx(), "DocumentNo"));
		rowFilterInvoice.appendChild(serialLabel.rightAlign());
		DocumentNoField = new WStringEditor();
		DocumentNoField.getComponent().setText("");
		DocumentNoField.getComponent().setWidth("96%");
//		DocumentNoField.setReadWrite(false);
		rowFilterInvoice.appendChild(DocumentNoField.getComponent());
		
		//Partner
		Label bpartnerLabel = new Label(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		rowFilterInvoice.appendChild(bpartnerLabel.rightAlign());
		MLookup lookupBP = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 0, SystemIDs.COLUMN_C_INVOICE_C_BPARTNER_ID, DisplayType.Search);
		bpartnerSearch = new WSearchEditor("C_BPartner_ID", true, false, true, lookupBP);
		bpartnerSearch.getComponent().setWidth("100");
		rowFilterInvoice.appendChild(bpartnerSearch.getComponent());

		// ORGANIZATION
		Label organizationLabel = new Label(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		rowFilterInvoice.appendChild(organizationLabel.rightAlign());
		MLookup lookupOrg = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.COLUMN_C_PERIOD_AD_ORG_ID, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_Org_ID", 0, false, "(AD_Org.AD_Org_ID != 0 AND AD_Org.IsSummary = 'N')");
		organizationPick = new WTableDirEditor("AD_Org_ID", true, false, true, lookupOrg);
		organizationPick.getComponent().setWidth("100%");
		rowFilterInvoice.appendCellChild(organizationPick.getComponent(),2);
        organizationPick.setValue(AD_Org_ID);
		
		//New Row
		rowFilterInvoice = rowsFilterInvoice.newRow();

		// is Delived
		Div divDelivered = new Div();
		Label isDeliveredLabel = new Label("Facturas Entregadas");
		divDelivered.appendChild(isDeliveredLabel.rightAlign());
		isDelivered = new Checkbox();
		divDelivered.appendChild(isDelivered);	
		rowFilterInvoice.appendChild(divDelivered);	
		
		
		deliveredNoFieldSearchL = new Label("Número de Entrega");
		deliveredNoFieldSearchL.setVisible(false);
		rowFilterInvoice.appendChild(deliveredNoFieldSearchL.rightAlign());
   		deliveredNoFieldSearch = new WStringEditor();
   		deliveredNoFieldSearch.setVisible(false);
   		rowFilterInvoice.appendChild(deliveredNoFieldSearch.getComponent());
		
		dateDeliveredLabel = new Label();
		dateDeliveredLabel.setText("Fecha de Entrega");
		dateDeliveredLabel.setVisible(false);
		rowFilterInvoice.appendChild(dateDeliveredLabel.rightAlign());
		dateDelivered = new WDateEditor();
		dateDelivered.setVisible(false);
		dateDelivered.setValue(Env.getContextAsDate(Env.getCtx(), "@#Date@"));
		dateDelivered.getComponent().setWidth("100%");
		rowFilterInvoice.appendChild(dateDelivered.getComponent());
		
		Space space = new Space();
		rowFilterInvoice.appendChild(space);
		
		Space space2 = new Space();
		rowFilterInvoice.appendChild(space);
		
		rowFilterInvoice.appendChild(space2);

		Div divSearchButton = new Div();
		rowFilterInvoice.appendChild(divSearchButton);
		searchButton = new Button();
		searchButton.setLabel("Buscar");
		divSearchButton.appendChild(searchButton);
		
		
		
		   cleanButton = new Button();
		   cleanButton.setLabel("Limpiar Todo");
			divSearchButton.appendChild(cleanButton);
		   rowFilterInvoice.appendCellChild(divSearchButton,2);

		// ***************************NORTH****************************//

		
		// ***************************CENTER****************************//
		
		Center mainPanelCenter = new Center();
		mainLayout.appendChild(mainPanelCenter);
		
		//New Border Layout
		Borderlayout mainPanelCenterLayout = new Borderlayout();
		mainPanelCenter.appendChild(mainPanelCenterLayout);
		       
		      North northCenter = new North();
		      northCenter.setHeight("50%");
		      mainPanelCenterLayout.appendChild(northCenter);
		           
		           Borderlayout northCenterLayout = new Borderlayout();
		           northCenter.appendChild(northCenterLayout);
		           
			           Center centernorthC = new Center();
			           northCenterLayout.appendChild(centernorthC);
                       centernorthC.appendChild(invoiceTable);			           
			           
			           //Contiene los botenes para seleccionar las filas
			           East eastNorthC = new East();
			           eastNorthC.setWidth("12%");
			           northCenterLayout.appendChild(eastNorthC);	
			   		
			   		   Grid gridButton = new Grid();
			   		   eastNorthC.appendChild(gridButton);
			   		   Rows rowsButton = gridButton.newRows();
			   		   Row  rowButton = rowsButton.newRow();
			   		
				   	   Div div = new Div();
				   	   selectAllButton = new Button();
				   	   selectAllButton.setLabel("Seleccionar Todos");
				   	   div.appendChild(selectAllButton);
			   		
			   		   rowButton.appendChild(div);
			   		
			   		   rowButton = rowsButton.newRow();
			   		   Div divInfo = new Div();
			   		   labelInvoiceNumber = new Label("");
			   		   divInfo.appendChild(labelInvoiceNumber);
			   		   divInfo.setHeight("45px");
			   		   rowButton.appendCellChild(divInfo);
			   		 
			   		   South southNorthC = new South();
			   		   northCenterLayout.appendChild(southNorthC);
			   		   
				       Grid gridAdd = new Grid();
				       southNorthC.appendChild(gridAdd);
					   Rows rowsAdd = gridAdd.newRows();
					   Row  rowAdd = rowsAdd.newRow();
			   		   btnAddInvoice = new Button("Añadir Facturas");
			   		   rowAdd.appendChild(btnAddInvoice);
			   		   
			   		   asignar = new Label("Asignar a: ");
			   		   rowAdd.appendChild(asignar.rightAlign());			   
					   salesRepD = new WTableDirEditor("C_BPartner_ID", true, false, true, lookupSR);
					   salesRepD.getComponent().setWidth("100%");

					   rowAdd.appendChild(salesRepD.getComponent());
					   rowAdd.appendChild(new Space());
					   rowAdd.appendChild(new Space());
					   rowAdd.appendChild(new Space());
    
		      South southCenter = new South();
		      southCenter.setHeight("50%");
		      southCenter.setTitle("Facturas a Procesar");
		      mainPanelCenterLayout.appendChild(southCenter);
		      
		      Borderlayout southCenterLayout = new Borderlayout();
		      southCenter.appendChild(southCenterLayout);
		      
		           Center centerSouthC = new Center();
		           southCenterLayout.appendChild(centerSouthC);
		           centerSouthC.appendChild(invoiceTableToProcess);
//		           
		           East eastCenterSouthC = new East();
		           eastCenterSouthC.setWidth("12%");
		           southCenterLayout.appendChild(eastCenterSouthC);
		           btnDeleteInvoice = new Button("Eliminar");
		           eastCenterSouthC.appendChild(btnDeleteInvoice);
//		           
		      
		   		// ***************************END CENTER****************************//
		   		
		   		// ***************************SOUTH****************************//
		   
		   
		   		South southLayout = new South();
		   		mainLayout.appendChild(southLayout);
		   		
		   		Grid gridSouth = GridFactory.newGridLayout();
		   		southLayout.appendChild(gridSouth);
		   		Rows gridSouthRows = gridSouth.newRows();
		   		Row gridSouthRow = gridSouthRows.newRow();
		   		
		   		dateDeliveredL = new Label();
		   		dateDeliveredL.setText("Fecha de Entrega(Facturas a Procesar)");
		   		gridSouthRow.appendChild(dateDeliveredL.rightAlign());
		   		dateDeliveredP = new WDateEditor();
		   		dateDeliveredP.setValue(Env.getContextAsDate(Env.getCtx(), "@#Date@"));
		   		dateDeliveredP.getComponent().setWidth("100%");
		   		rowFilterInvoice.appendChild(dateDeliveredP.getComponent());
		   		gridSouthRow.appendChild(dateDeliveredP.getComponent());
		   		
		   		
		   		noteLabel = new Label(Msg.translate(Env.getCtx(), "Note"));
		   		gridSouthRow.appendChild(noteLabel.rightAlign());
		   		noteField = new WStringEditor();
		   		noteField.getComponent().setWidth("100%");
		   		gridSouthRow.appendCellChild(noteField.getComponent(),2);
		   		
		   		deliveredNoLabel = new Label(Msg.translate(Env.getCtx(), "Número de Entrega"));
		   		gridSouthRow.appendChild(deliveredNoLabel.rightAlign());
		   		deliveredNoField = new WStringEditor();
		   		deliveredNoField.getComponent().setReadonly(true);
		   		deliveredNoField.getComponent().setText(sequence.getCurrentNext()+"");
		   		gridSouthRow.appendChild(deliveredNoField.getComponent());		
		   		
		   		processButton = new Button();
		   		processButton.setLabel("Procesar");
		   		gridSouthRow.appendChild(processButton);
		   		
		   		
		   		///////////////////AÑADIR EVENTOS////////////////
		   		isDelivered.addEventListener(Events.ON_CLICK, this);
		   		searchButton.addEventListener(Events.ON_CLICK, this);
		   		selectAllButton.addEventListener(Events.ON_CLICK, this);
		   		btnAddInvoice.addEventListener(Events.ON_CLICK, this);
		   		btnDeleteInvoice.addEventListener(Events.ON_CLICK, this);
		   		processButton.addEventListener(Events.ON_CLICK, this);
		   		cleanButton.addEventListener(Events.ON_CLICK, this);
		   		salesRep.getComponent().addEventListener(Events.ON_CHANGE, this);
		   		salesRepD.getComponent().addEventListener(Events.ON_CHANGE, this);
		   		
		   		DocumentNoField.getComponent().addEventListener(Events.ON_OK, this);
		   		DocumentNoField.getComponent().addEventListener(Events.ON_CHANGE, this);
		   		deliveredNoFieldSearch.getComponent().addEventListener(Events.ON_OK, this);
		   		salesRep.getComponent().addEventListener(Events.ON_OK, this);
		   		bpartnerSearch.addValueChangeListener(this); 
		   		organizationPick.addValueChangeListener(this);
	}
	
	private void loadGlobalVariables() {
		invoiceTable = ListboxFactory.newDataTable();
		invoiceTable.addColumn(IDColumn.class, false, "");
		invoiceTable.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "DateInvoiced"));
		invoiceTable.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "DocumentNo"));
		invoiceTable.addColumn(KeyNamePair.class, true, "Vendedor");
		invoiceTable.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "Value"));
		invoiceTable.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		invoiceTable.addColumn(BigDecimal.class, true,Msg.translate(Env.getCtx(), "GrandTotal"));
		invoiceTable.addColumn(BigDecimal.class, true,Msg.translate(Env.getCtx(), "OpenAmt"));
		invoiceTable.repaint();	
		
		invoiceTableToProcess = ListboxFactory.newDataTable();
		invoiceTableToProcess.addColumn(IDColumn.class, false, "");
		invoiceTableToProcess.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "DateInvoiced"));
		invoiceTableToProcess.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "DocumentNo"));
		invoiceTableToProcess.addColumn(KeyNamePair.class, true, "Vendedor");
		invoiceTableToProcess.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "Value"));
		invoiceTableToProcess.addColumn(KeyNamePair.class, true, Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		invoiceTableToProcess.addColumn(BigDecimal.class, true,Msg.translate(Env.getCtx(), "GrandTotal"));
		invoiceTableToProcess.addColumn(BigDecimal.class, true,Msg.translate(Env.getCtx(), "OpenAmt"));
		invoiceTableToProcess.repaint();
		sequence = getSequence();
		
		
	}
    public MSequence getSequence(){
	    	MSequence sequence = new Query(Env.getCtx(), MSequence.Table_Name, "Name = ?", null).setParameters("DCS_DeliveredNo").first();
			if (sequence == null){
				   sequence = new MSequence(Env.getCtx(), 0, null);
				   sequence.setName("DCS_DeliveredNo");
				   sequence.setIsAutoSequence(true);
				   sequence.save();
			}
	    return sequence;
	    }
    @Override
    public void onEvent(Event event) throws Exception {	
			if (event.getTarget().equals(isDelivered)) {
				enableFieldUnDelivered();
				if (salesRep.getValue()!=null || isDelivered.isChecked())
				     searchInvoice();
			}else if (event.getTarget().equals(searchButton)) {
				searchInvoice();
				}
			else if(event.getTarget().equals(selectAllButton)){
				selectAll();
			}
			else if(event.getTarget().equals(processButton)){
				processInvoice();
			}
			else if(event.getTarget().equals(cleanButton)){
				cleanButton();
			}
			else if(event.getTarget().equals(salesRep.getComponent())){
				if (salesRep.getComponent().getText().isEmpty()){
					salesRep.setValue(null);
					if(isDelivered.isChecked())
						searchInvoice();
					else
						invoiceTable.clearTable();
				}else{
					if(linesDataTableInvoiceToProcess.isEmpty()){
						salesRepD.setValue(salesRep.getValue());
					}
					searchInvoice();

				}
			}
			else if(event.getTarget().equals(bpartnerSearch.getComponent().getTextbox())){
				if (bpartnerSearch.getComponent().getText().isEmpty())
					bpartnerSearch.setValue(null);

				searchInvoice();
			}
			else if(event.getTarget().equals(DocumentNoField.getComponent())){
				if (!salesRep.getComponent().getText().isEmpty())
					 searchInvoice();
			}
			else if(event.getTarget().equals(deliveredNoFieldSearch.getComponent())){
				searchInvoice();
		}
			else if(event.getTarget().equals(btnAddInvoice)){
				addInvoiceToProcess();
			}
			else if(event.getTarget().equals(btnDeleteInvoice)){
				deleteInvoiceToProcess();
			}
			else if(event.getTarget().equals(salesRepD)){
				if (salesRepD.getComponent().getText().isEmpty()){
					salesRepD.setValue(null);
				}
			}		
		}		
		private void deleteInvoiceToProcess() {
			Vector<Vector<Object>> lineDataTemp = new Vector<Vector<Object>>(); 		
			for (Vector<Object> line : linesDataTableInvoiceToProcess) {
				if (line.get(0).equals(Boolean.FALSE)) {
					lineDataTemp.add(line);
				}
			}
			invoiceTableToProcess.clearTable();
			linesDataTableInvoiceToProcess = lineDataTemp;
			invoiceTableModelToProcess = new ListModelTable(linesDataTableInvoiceToProcess);
			invoiceTableModelToProcess.addTableModelListener(this);
			invoiceTableToProcess.setModel(invoiceTableModelToProcess);
			invoiceTableToProcess.repaint();	
		}

		private void addInvoiceToProcess() {
			
			Vector<Vector<Object>> lineDataTemp = new Vector<Vector<Object>>(); 
			for (Vector<Object> line : linesDataTableInvoice) {
				if(line.get(0).equals(Boolean.TRUE)){
    	           	line.set(0, false);
    	           	linesDataTableInvoiceToProcess.add(line);
				}else{
					lineDataTemp.add(line);
				}
			}
			
		    invoiceTableModelToProcess = new ListModelTable(linesDataTableInvoiceToProcess);
			invoiceTableModelToProcess.addTableModelListener(this);
			invoiceTableToProcess.setModel(invoiceTableModelToProcess);
			invoiceTableToProcess.repaint();
		}

		public void cleanButton(){
			salesRep.setValue(null);
			bpartnerSearch.setValue(null);
			organizationPick.setValue(AD_Org_ID);	
			invoiceTable.clearTable();
			invoiceTableToProcess.clearTable();
			linesDataTableInvoiceToProcess.clear();
			noteField.setValue("");
			labelInvoiceNumber.setValue("");
			labelInfo.setValue("");
			deliveredNoFieldSearch.setValue("");
			DocumentNoField.setValue("");
			salesRepD.setValue(null);
			
		}
		
		private void processInvoice() {
			if (salesRepD.getValue()==null)
				 throw new AdempiereException("Debe Asignar un Representante Comercial");
			else
				if (dateDeliveredP.getValue()== null)
					throw new AdempiereException("Debe seleccionar una fecha de Entrega");
			else{
		     String isNotDelivered = isDelivered.isChecked()?"N":"Y";
             Timestamp ts = (Timestamp) dateDeliveredP.getValue();
             String date = sdf.format(ts);
             ts = Timestamp.valueOf(date);
             boolean isprocess = false;
				for (Vector<Object> line : linesDataTableInvoiceToProcess) {
					 isprocess = true;
					 PreparedStatement pst = DB.prepareStatement("UPDATE "
						 		  + " C_Invoice " 
								  + " set isDelivered = ? "
						 		  + " ,dateDelivered = ? "
						 		  + " ,note = ?  "
						 		  + " , deliveredNo= ?  "
						 		  + ", SalesRepDelivered_ID = ?"
								  + " where documentNo = ?",null);
						 try {
							pst.setString(1, isNotDelivered);
							if (isNotDelivered.equals("Y")){ 
								pst.setTimestamp(2, ts);
								pst.setString(4, deliveredNoField.getComponent().getText());
								pst.setInt(5, Integer.parseInt(salesRepD.getValue().toString()));
							}else{
								pst.setNull(2, java.sql.Types.DATE);
								pst.setString(4, null);	
								pst.setNull(5, java.sql.Types.INTEGER);
							}
							pst.setString(3, isDelivered.isChecked()?"":noteField.getValue().toString());
							pst.setString(6, line.get(2).toString());
							pst.executeQuery();	
							pst.close();
						 }catch (Exception e) {}
				    	finally{
				    		DB.close(pst);
				    		pst = null;
				    	}	
				}
				if (isprocess){
                    String deliveredNoprocess = deliveredNoField.getComponent().getText();
					if(isNotDelivered.equals("Y")){
						sequence.getNextID();
						sequence.save();
						int NextID =sequence.getCurrentNext();
						deliveredNoField.getComponent().setText(NextID+"");
						starReport(deliveredNoprocess);
					}
					cleanButton();

				}
				
				noteField.setValue("");
			}
		}
		
		public void starReport(String deliveredNo){
			MProcess process = new Query(Env.getCtx(), MProcess.Table_Name, "name = ?", null).setParameters("List Processed Invoice").first();
			if (process!=null){
				ProcessInfo processInfo = new ProcessInfo(process.getName(), process.get_ID());
				MPInstance instance = new MPInstance(Env.getCtx(), processInfo.getAD_Process_ID(), processInfo.getRecord_ID());
				instance.save();
				ProcessInfoParameter[] para = {new ProcessInfoParameter("deliveredNo", deliveredNo, null, null, null)};
				processInfo.setAD_Process_ID(process.get_ID());
				processInfo.setClassName("org.compiere.report.ReportStarter");
				processInfo.setAD_PInstance_ID(instance.getAD_PInstance_ID());
				processInfo.setParameter(para);
				ProcessUtil.startJavaProcess(Env.getCtx(), processInfo,null,true);
			}
			
		}
		

		public void searchInvoice(){

			StringBuilder sql = isDelivered.isChecked()?searchInvoiceProcess():searchInvoiceUnProcess();
            linesDataTableInvoice  = new Vector<Vector<Object>>();
            ResultSet rs = null;
            PreparedStatement pstmt = null;
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/YYYY");
			try {
     			pstmt = DB.prepareStatement(sql.toString(), null);
    			rs = pstmt.executeQuery();
    			
    			while (rs.next()) {
    	             MInvoice invoice = new MInvoice(Env.getCtx(),rs.getInt("C_Invoice_ID"),null);
    	             if(invoice.getOpenAmt().compareTo(Env.ZERO)!=0){
	    	           	Vector<Object> line = new Vector<Object>();
						linesDataTableInvoice.add(line);
						line.add(Boolean.FALSE);
						line.add(sdf1.format(rs.getDate("dateinvoiced")));
						line.add(rs.getString("DocumentNo"));
						line.add(rs.getString("SalesRep"));	
						line.add(rs.getString("valuet"));	
						line.add(rs.getString("namet"));
						line.add(rs.getBigDecimal("GrandTotal"));
						line.add(invoice.getOpenAmt());												
    	            }

    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		finally
    		{
    			DB.close(rs, pstmt);
    			rs = null; pstmt = null;
    		}
			
			labelInvoiceNumber.setValue("Nro. de Facturas: "+linesDataTableInvoice.size());	
			ListModelTable invoiceTableModel = new ListModelTable(linesDataTableInvoice);
			invoiceTableModel.addTableModelListener(this);
			invoiceTable.setModel(invoiceTableModel);
			invoiceTable.repaint();		
		}
		
		
		public StringBuilder searchInvoiceUnProcess(){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM ( ");
			sql.append("SELECT ci.C_Invoice_ID,ci.DocumentNo,au.name AS SalesRep,cb.value AS valueT,ci.C_BPartner_ID,cb.name AS namet, ");
			sql.append("ci.Dateinvoiced,ci.GrandTotal,ci.dateDelivered,ci.deliveredno,ci.AD_Org_ID,ci.isDelivered ");
			sql.append("FROM C_Invoice ci ");
			sql.append("JOIN C_Doctype cit ON ci.c_doctypeTarget_id =cit.c_doctype_id ");
			sql.append("JOIN C_BPartner cb ON cb.C_BPartner_ID = ci.C_BPartner_ID  ");
			sql.append("JOIN AD_User au ON au.AD_User_ID = ci.SalesRep_ID  ");
			sql.append("WHERE ci.issotrx = 'Y' ");
			sql.append("AND ci.docstatus IN ('CO','CL')  ");
			 if(salesRep.getValue()!=null){
				sql.append(" AND ci.SalesRep_ID = ").append(salesRep.getValue());
			 }
			sql.append(" UNION ");
			sql.append("SELECT ci.C_Invoice_ID,ci.DocumentNo,au.name AS SalesRep,cb.value AS valueT,ci.C_BPartner_ID,cb.name, ");
			sql.append("ci.Dateinvoiced,ci.GrandTotal,ci.dateDelivered,ci.deliveredno,ci.AD_Org_ID,ci.isDelivered ");
			sql.append("FROM C_invoice ci ");
			sql.append("JOIN C_Order co ON co.C_Order_ID = ci.C_Order_ID  ");
			sql.append("JOIN C_BPartner_Location cbl ON cbl.C_BPartner_Location_ID = co.C_BPartner_Location_ID  ");
			sql.append("JOIN C_BPartner cb ON cb.C_BPartner_ID = ci.C_BPartner_ID  ");
			sql.append("JOIN AD_User au ON au.AD_User_ID = cbl.SalesRep_ID ");
			sql.append("WHERE ci.issotrx = 'Y' ");
			sql.append("AND ci.docstatus IN ('CO','CL')  ");
			if(salesRep.getValue()!=null){
			   sql.append(" AND ci.SalesRep_ID != ").append(salesRep.getValue());
			}
			sql.append(" AND ci.C_Bpartner_ID in(SELECT ci.C_Bpartner_ID  ");
			sql.append("FROM C_Invoice ci ");
			sql.append("JOIN C_Order co ON co.C_Order_ID = ci.C_Order_ID  ");
			sql.append("JOIN C_BPartner_Location cbl ON cbl.C_BPartner_Location_ID = co.C_BPartner_Location_ID  ");
			sql.append("WHERE ci.issotrx = 'Y' ");
			sql.append("AND ci.docstatus IN ('CO','CL')  ");
			sql.append("AND ci.isDelivered = 'N' ");
			 if(salesRep.getValue()!=null){
				sql.append(" AND ci.SalesRep_ID = ").append(salesRep.getValue());
			 }
			sql.append(" AND cbl.IsBillTO = 'Y' )) as INVOICES ");
             sql.append(" WHERE isDelivered = 'N'");

			 if(!bpartnerSearch.getComponent().getText().isEmpty()){
				sql.append(" AND C_BPartner_ID = ").append(bpartnerSearch.getValue());
			 }

			 if (isDelivered.isChecked() && dateDelivered.getValue()!= null){
				 Timestamp ts = (Timestamp) dateDelivered.getValue();
				 sql.append(" AND DateDelivered = '").append(sdf.format(ts)).append("'");	 
			 }
		 
			 if (organizationPick.getValue()!=null &&!organizationPick.getValue().equals(0)){
				 sql.append(" AND AD_Org_ID = ").append(organizationPick.getValue());	 
			 }
			 if ( !"".equals(DocumentNoField.getValue())){
				 sql.append(" AND DocumentNo LIKE '%").append(DocumentNoField.getComponent().getText().toUpperCase()).append("%'");	 
			 }
//			 
             sql.append(" ORDER BY  valuet,DocumentNo");
			return sql;
			
		}
		
		public StringBuilder searchInvoiceProcess(){
			StringBuilder sql = new StringBuilder();
			 sql.append(" Select ci.C_Invoice_ID,ci.DocumentNo,ci.C_BPartner_ID,cb.name as NameT,cb.value as valueT, ci.Dateinvoiced,ci.GrandTotal,au.name as salesrep,citt.name as doctype,ci.dateDelivered from C_Invoice ci ");
			 sql.append(" JOIN C_Doctype cit ON ci.c_doctypeTarget_id =cit.c_doctype_id");
			 sql.append(" JOIN C_DocType_Trl citt ON cit.c_doctype_id=citt.c_doctype_id AND citt.AD_Language='"+ Env.getAD_Language(Env.getCtx())+"'");
			 sql.append(" JOIN AD_User au ON au.AD_User_ID = ci.salesRepdelivered_ID  ");
			 sql.append	("JOIN C_BPartner cb ON cb.C_BPartner_ID = ci.C_BPartner_ID WHERE ci.issotrx = 'Y'");
			 
			 if(!bpartnerSearch.getComponent().getText().isEmpty()){
				sql.append(" AND cb.C_BPartner_ID = ").append(bpartnerSearch.getValue());
			 }
			 if(salesRep.getValue()!=null){
				sql.append(" AND ci.SalesRep_ID = ").append(salesRep.getValue());
			 }
			 else{
				 invoiceTable.clearTable();
				 labelInvoiceNumber.setValue("");
//				 throw new AdempiereException("Selecccione un Representante Comercial");
			 }
			 if (isDelivered.isChecked() && dateDelivered.getValue()!= null){
				 Timestamp ts = (Timestamp) dateDelivered.getValue();
				 sql.append(" AND ci.DateDelivered = '").append(sdf.format(ts)).append("'");	 
			 }
			 
			 if (organizationPick.getValue()!=null){
				 sql.append(" AND ci.AD_Org_ID = ").append(organizationPick.getValue());	 
			 }
			 if ( !"".equals(DocumentNoField.getValue())){
				 sql.append(" AND ci.DocumentNo LIKE '%").append(DocumentNoField.getComponent().getText().toUpperCase()).append("%'");	 
			 }
			 if (isDelivered.isChecked() && deliveredNoFieldSearch.getValue()!=""){
				 sql.append(" AND deliveredNo = '").append(deliveredNoFieldSearch.getComponent().getText().toUpperCase()).append("'");	 
			 }
			 

            sql.append(" AND isDelivered = 'Y' ");
            sql.append(" AND ci.docstatus IN ('CO','CL') ORDER BY  valuet,DocumentNo");
			return sql;
			
		}
		
		public void enableFieldUnDelivered(){
			
			if (isDelivered.isChecked()){
				dateDelivered.setVisible(true);
				deliveredNoFieldSearchL.setVisible(true);
				deliveredNoFieldSearch.setVisible(true);
				dateDeliveredP.setVisible(false);
				noteField.setVisible(false);
				dateDeliveredL.setVisible(false);
				dateDeliveredLabel.setVisible(true);
				noteLabel.setVisible(false);
				processButton.setLabel("Desprocesar");
				noteField.setValue("");
				deliveredNoField.setVisible(false);
				deliveredNoLabel.setVisible(false);
				salesRepD.setVisible(false);
				asignar.setVisible(false);
				
//				salesRep.setReadWrite(false);
//				DocumentNoField.setReadWrite(false);
//				bpartnerSearch.setReadWrite(false);
//				organizationPick.setReadWrite(false);
			}else{
				processButton.setLabel("Procesar");
				dateDelivered.setVisible(false);
				dateDeliveredP.setVisible(true);
				deliveredNoFieldSearchL.setVisible(false);
				deliveredNoFieldSearch.setVisible(false);
				noteField.setVisible(true);
				dateDeliveredL.setVisible(true);
				dateDeliveredLabel.setVisible(false);
				noteLabel.setVisible(true);
				deliveredNoField.setVisible(true);
				deliveredNoLabel.setVisible(true);
				salesRepD.setVisible(true);
				asignar.setVisible(true);
				
//				salesRep.setReadWrite(true);
//				DocumentNoField.setReadWrite(true);
//				bpartnerSearch.setReadWrite(true);
//				organizationPick.setReadWrite(true);
				
			}
			dateDelivered.setValue(null);
			invoiceTable.clearTable();
			invoiceTableToProcess.clearTable();
			linesDataTableInvoiceToProcess.clear();
			labelInvoiceNumber.setValue("");
			
			
		}
		
		private void selectAll() {
			if (selectAllButton.getLabel().equals("Seleccionar Todos")) {
				for (Vector<Object> line : linesDataTableInvoice) {
					if(line.get(0).equals(Boolean.FALSE))
						line.set(0,Boolean.TRUE);
				}
				selectAllButton.setLabel("Deseleccionar Todos");
			}else{
				unSelectAll();
				selectAllButton.setLabel("Seleccionar Todos");
				
			}
			invoiceTable.repaint();
		}

		private void unSelectAll() {
			for (Vector<Object> line : linesDataTableInvoice) {
				if(line.get(0).equals(Boolean.TRUE))
					line.set(0,Boolean.FALSE);
			}

		}
		@Override
		public void tableChanged(WTableModelEvent event) {
		
			
		}

		@Override
		public void valueChange(ValueChangeEvent e) {
			String name = e.getPropertyName();
			Object value = e.getNewValue();
			if (name.equals("C_BPartner_ID"))
			{
				bpartnerSearch.setValue(value);
                searchInvoice();
			}
			// Organization
			if (name.equals("AD_Org_ID"))
			{
				if (salesRep.getValue()!=null)
					searchInvoice();
			}
			
		}

}