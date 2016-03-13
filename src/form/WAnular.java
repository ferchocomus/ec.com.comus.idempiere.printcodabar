package form;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.util.Callback;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MInventory;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.North;
import org.zkoss.zul.South;

public class WAnular  extends ADForm implements IFormController{
/**
	 * 
	 */
private static final long serialVersionUID = -5779187375101512112L;

	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WAnular.class);

	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	private WListbox receiptLineTable;
	private Vector<Vector<Object>> lineData;
	private Hbox northPanel = new Hbox();
	private Div centerPanel = new Div();
	private ListModelTable receiptLineTableModel;
	protected void initForm()
	{
		log.info("");
		try
		{
			jbInit();
			dynInit();
			
			this.setWidth("50%");
			this.setHeight("50%");
			this.setClosable(true);
			this.setTitle("Anular Ingresos");
			this.setBorder("normal");
			
			Borderlayout layout = new Borderlayout();
			layout.setHeight("100%");
			layout.setWidth("100%");
			this.appendChild(layout);
			North north = new North();
			layout.appendChild(north);
			north.appendChild(northPanel);
			Center center = new Center();
			layout.appendChild(center);
			center.appendChild(centerPanel);
			centerPanel.setVflex("1");
			centerPanel.setHflex("1");
			South south = new South();
			layout.appendChild(south);
			new Label();
			south.appendChild(confirmPanel);
			confirmPanel.getButton("Ok").setEnabled(false);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "init", e);
		}
		searchMInventory();
	}	//	init

	/**
	 *	Static Init
	 *  @throws Exception
	 */
	
	private void jbInit() throws Exception{
		
		receiptLineTable = ListboxFactory.newDataTable();

		receiptLineTable.addColumn(Boolean.class, false, Msg.translate(Env.getCtx(), "Select"));
		receiptLineTable.addColumn(KeyNamePair.class, false, Msg.translate(Env.getCtx(), "DocumentNo"));
		receiptLineTable.addColumn(String.class, true, Msg.translate(Env.getCtx(), "Description"));
		
		receiptLineTable.setStyle("text-align: center");
		receiptLineTable.repaint();
		
		centerPanel.appendChild(receiptLineTable);
		
		confirmPanel.addActionListener(Events.ON_CLICK, this);
	
	}
	
	/**
	 *	Dynamic Init
	 */
	
	private void dynInit(){

	}	

	
	public void onEvent(Event e) throws Exception {		

	 if (e.getTarget() == confirmPanel.getButton("Ok")){
			FDialog.ask(getWindowNo(), this, "Desea Anular los Registros Seleccionados?", new Callback<Boolean>() {	
				@Override
				public void onCallback(Boolean result) {
					if (result){
						anular();
						confirmPanel.setEnabled("Ok", false);
					}
				}
			});	

			
		}
		else if (e.getTarget() == confirmPanel.getButton("Cancel")){
			this.dispose();
			return;
		}
		
		if (receiptLineTableModel.size() >0)	//	format loaded
			confirmPanel.getButton("Ok").setEnabled(true);
		else
			confirmPanel.getButton("Ok").setEnabled(false);
	}
	
	private void anular() {		
		for (Vector<Object> line : lineData) {
			if (line.get(0).equals(Boolean.TRUE)) {
				int M_Inventory_ID = ((KeyNamePair)line.get(1)).getKey();
				MInventory inventory = new MInventory(Env.getCtx(), M_Inventory_ID, null);
				inventory.setDocAction(DocAction.ACTION_Void);
				inventory.voidIt();
				inventory.save();
			}
		}		
		searchMInventory();	
	}
	
	private void searchMInventory() {

		List<MInventory> inventory = getListMInventory();
		if (inventory.size()==0)
			confirmPanel.getButton("Ok").setEnabled(false);
		else
			confirmPanel.getButton("Ok").setEnabled(true);
		
		lineData = new Vector<Vector<Object>>();
		 for (MInventory mInventory : inventory) {
				Vector<Object> line = new Vector<Object>();
				lineData.add(line);
				line.add(Boolean.FALSE);
				line.add(new KeyNamePair(mInventory.get_ID(), mInventory.getDocumentNo()) );
				line.add(mInventory.getDescription() );		
		}
			receiptLineTableModel = new ListModelTable(lineData);
			receiptLineTable.setModel(receiptLineTableModel);
			((Listheader) receiptLineTable.getListHead().getChildren().get(0)).setWidth("10%");
			((Listheader) receiptLineTable.getListHead().getChildren().get(1)).setWidth("20%");
			((Listheader) receiptLineTable.getListHead().getChildren().get(2)).setWidth("70%");
			receiptLineTable.repaint();
			confirmPanel.getButton("Ok").setEnabled(true);	
	}

	public List<MInventory> getListMInventory(){
		
		List<MInventory> listInventory = new ArrayList<MInventory>(); ;
		String andWhere = "";
		if (CodaBarMain.variedadSelected!=null){
			 andWhere = "and ml.M_Product_ID = '"+CodaBarMain.variedadSelected.get_ID()+"'";
		}
		//
		String sql = "Select m.* from M_Inventory m JOIN M_InventoryLine ml ON ml.M_Inventory_ID = m.M_Inventory_ID"+
				     " where C_DocType_ID=1000045 and movementdate = ? and docstatus = 'CO' "+andWhere;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setTimestamp(1, CodaBarMain.date);
			rs = pstmt.executeQuery ();
			while (rs.next ()){
				listInventory.add(new MInventory (Env.getCtx(), rs, null));
			}
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return listInventory;
	}
	
	public void procesar(){
		for (Vector<Object> line : lineData) {
			System.out.println(line.get(0)+" "+((KeyNamePair)line.get(1)).getKey());	
		}		
	}		

	@Override
	public ADForm getForm() {
		return null;
	}
	public Mode getWindowMode() {
		return Mode.HIGHLIGHTED;
	}
}