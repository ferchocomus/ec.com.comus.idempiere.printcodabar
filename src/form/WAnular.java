package form;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import org.adempiere.util.Callback;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MInventory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductPrice;
import org.compiere.model.MStorageOnHand;
import org.compiere.model.Query;
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
import org.zkoss.zul.Decimalbox;
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
	private WSearchEditor productSearch;
	private Decimalbox productQtyField;
	private int p_M_Order_ID;
	private Label errorLabel;


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
			errorLabel = new Label();
			south.appendChild(confirmPanel);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "init", e);
		}
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
		searchMInventory();
	}
	
	/**
	 *	Dynamic Init
	 */
	
	private void dynInit()
	{

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
		List<MInventory> inventory = new Query(Env.getCtx(),MInventory.Table_Name, "movementdate = ? and docstatus = 'CO'", null).setParameters(CodaBarMain.date).list();
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

	
	public void procesar(){
		for (Vector<Object> line : lineData) {
			System.out.println(line.get(0)+" "+((KeyNamePair)line.get(1)).getKey());
		
			
		}	
		
	}
	public void addOrderLine(String serial){
		
		MOrder order = new MOrder(Env.getCtx(), p_M_Order_ID, null);
	
				int productID = Integer.parseInt(productSearch.getValue().toString());
				int M_AttributeSetInstance_ID = 0;
				BigDecimal kilos =  productQtyField.getValue();
				MProductPrice productPrice = verifyProductONPriceList( productID, order.getM_PriceList_ID()); // Verifica que el producto este en la lista de precio que se seleccionó en la orden
				
				if (productPrice!=null){
					MOrderLine orderLine = new Query(Env.getCtx(), MOrderLine.Table_Name, "M_Product_ID = ? AND M_AttributeSetInstance_ID = ? AND C_Order_ID = ?", null)
					.setParameters(productID,M_AttributeSetInstance_ID,p_M_Order_ID).first();
					if (orderLine == null){
						if (verifyInventory(order,productID,M_AttributeSetInstance_ID,kilos)){
	
							    MOrderLine ol = new MOrderLine(Env.getCtx(),0,order.get_TrxName());
							    ol.setOrder(order);
								ol.setM_Product_ID(productID);
								ol.setQty(kilos);
								ol.setC_Order_ID(p_M_Order_ID);
								ol.setAD_Org_ID(order.getAD_Org_ID());
								ol.save();

	
						}else{
						errorLabel.setValue("No hay inventario disponible para este producto");
						return;
						}
					}else errorLabel.setValue("Producto ya registrado en la orden actual");
					
				}else errorLabel.setValue("El producto no se encuentra en la lista de precio");
			}

	

	private boolean verifyInventory(MOrder order , int productID, int M_AttributeSetInstance_ID,BigDecimal kilos){
		
		MStorageOnHand[] storages = MStorageOnHand.getWarehouse(Env.getCtx(), 
				order.getM_Warehouse_ID(), productID, M_AttributeSetInstance_ID, 
				null, true, false, 0, order.get_TrxName());
			BigDecimal qty = Env.ZERO;
			for (int i = 0; i < storages.length; i++)
			{
				if (storages[i].getM_AttributeSetInstance_ID() == M_AttributeSetInstance_ID)
					qty = qty.add(storages[i].getQtyOnHand());
			}
			
			if (kilos.compareTo(qty) > 0)
			{
				log.warning("Qty - Stock=" + qty + ", Ordered=" + kilos);
				log.saveError("QtyInsufficient", "=" + qty); 
				return false;
			}else
				return true;
	}
	private MProductPrice verifyProductONPriceList(int M_Product_ID, int M_PriceList_ID){
		
		String sql = "select plv.M_PriceList_ID as priceList from M_PriceList pl " +
				" JOIN M_PriceList_Version plv ON plv.M_PriceList_ID = pl.M_PriceList_ID"+
				" JOIN M_ProductPrice pp ON pp.M_PriceList_Version_ID = plv.M_PriceList_Version_ID"+
				" where pp.M_Product_ID = ? AND pl.M_PriceList_ID = ?" ;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, M_Product_ID);
			pstmt.setInt(2, M_PriceList_ID);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
			   return new MProductPrice(Env.getCtx(), rs.getInt("priceList"), M_Product_ID, null);
			}
		}
		catch (SQLException e)
		{
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return null;
	}
		

	@Override
	public ADForm getForm() {
		return null;
	}
	public Mode getWindowMode() {
		return Mode.HIGHLIGHTED;
	}
}