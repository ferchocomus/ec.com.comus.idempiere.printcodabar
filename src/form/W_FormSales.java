package form;

import java.util.List;
import java.util.Vector;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.SystemIDs;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.North;
import org.adempiere.webui.component.Row;
import org.zkoss.zul.South;
import org.zkoss.zul.Space;

public class W_FormSales implements IFormController, EventListener<Event>, WTableModelListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3484940261336891298L;
	private CustomForm form = new CustomForm();
	private WListbox variedadTable;
	private Button button0;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button buttonA;
	private Button buttonB;
	private Button buttonC;
	private Button buttonD;
	private Button buttonE;
	private Button buttonF;
	private Button buttonG;
	private Button buttonH;
	private Button buttonI;
	private Button buttonJ;
	private Button buttonK;
	private Button buttonL;
	private Button buttonM;
	private Button buttonN;
	private Button buttonO;
	private Button buttonP;
	private Button buttonQ;
	private Button buttonR;
	private Button buttonS;
	private Button buttonT;
	private Button buttonU;
	private Button buttonV;
	private Button buttonW;
	private Button buttonX;
	private Button buttonY;
	private Button buttonZ;
	private Button buttonAceptar;
	private Button buttonBorrar;
	private Button buttonAnular;
	private Button buttonInv;
	private Button buttonOrden;
	private Button buttonReemb;
	private Button buttonImpresion;
	private Button buttonBQTS;
	private Button buttonDo;
	private Button buttonLu;
	private Button buttonMi;
	private Button buttonMa;
	private Button buttonJu;
	private Button buttonVi;
	private Button buttonSa;

	public W_FormSales(){
		try {
			zkInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};
	
	protected void zkInit() throws Exception {
		

		Borderlayout mainLayout = new Borderlayout();
		form.appendChild(mainLayout);
		
		// ***************************Center****************************//
		Center mainPanelCenter = new Center();
//		mainPanelCenter.setWidth("60%");
		mainLayout.appendChild(mainPanelCenter);
		
		Div divCenter = new Div();
		divCenter.setStyle("border: solid 1px black");
		mainPanelCenter.appendChild(divCenter);
		
		Grid gridCenter1 = new Grid();
		divCenter.appendChild(gridCenter1);
		gridCenter1.setStyle("background-color:yellow; ");
		Rows rowsCenter = gridCenter1.newRows();
		
		Row rowCenter = rowsCenter.newRow();		
		Label lblVariedad = new Label("Variedad");
		Label lblFinca = new Label("Finca");	
		rowCenter.appendChild(lblVariedad);
		rowCenter.appendChild(lblFinca);
		
		rowCenter = rowsCenter.newRow();
		WStringEditor editorVariedad = new WStringEditor();
		MLookup lookupOrg = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.COLUMN_C_PERIOD_AD_ORG_ID, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_Org_ID", 0, false, "(AD_Org.AD_Org_ID != 0 AND AD_Org.IsSummary = 'N')");
		WTableDirEditor organizationPick = new WTableDirEditor("AD_Org_ID", true, false, true, lookupOrg);
		rowCenter.appendChild(editorVariedad.getComponent());
		rowCenter.appendChild(organizationPick.getComponent());
		
		
		Grid gridCenter2 = new Grid();
		divCenter.appendChild(gridCenter2);
		Rows rowsCenter2 = gridCenter2.newRows();
		
		Row rowCenter2 = rowsCenter2.newRow();
		Label lblTipoBunch = new Label("Tipo de Bunch");
		Label lblLongitud = new Label("Longitud");	
		Label lblTallos = new Label("Tallos");	
		Label lblMesas = new Label("Mesa");
		Label lblEtiquetas = new Label("Etiquetas");
		rowCenter2.appendChild(lblTipoBunch);
		rowCenter2.appendChild(lblLongitud);
		rowCenter2.appendChild(lblTallos);
		rowCenter2.appendChild(lblMesas);
		rowCenter2.appendChild(lblEtiquetas);
		
		rowCenter2 = rowsCenter2.newRow();
		WStringEditor wsTipoBunch = new WStringEditor();
		WStringEditor wsLongitud = new WStringEditor();	
		WStringEditor wsTallos = new WStringEditor();	
		WStringEditor wsMesas = new WStringEditor();
		WStringEditor wsEtiquetas = new WStringEditor();
		rowCenter2.appendChild(wsTipoBunch.getComponent());
		rowCenter2.appendChild(wsLongitud.getComponent());
		rowCenter2.appendChild(wsTallos.getComponent());
		rowCenter2.appendChild(wsMesas.getComponent());
		rowCenter2.appendChild(wsEtiquetas.getComponent());
		
		Grid gridCenterNumbers= new Grid();
		divCenter.appendChild(gridCenterNumbers);
		Rows rowsCenterNumbers = gridCenterNumbers.newRows();
		
		
		//////////////////////////////////////// Style Buttons ////////////////////////////////////////////////////////////////7777
		String width = "70px";
		String height = "55px";
		String styleButtonLetter = "width:"+width+"; height:"+height+";font-size: 20px;font-style: italic";
		String styleRowLetter ="width:"+width+"; height:"+height;
		
		Row rowCenterNumber = rowsCenterNumbers.newRow();
		rowCenterNumber.setStyle("height:60");
		button0 = new Button("0");
		button1 = new Button("1");
		button2 = new Button("2");
		button3 = new Button("3");
		button4 = new Button("4");
		button5 = new Button("5");
		button6 = new Button("6");
		button7 = new Button("7");
		button8 = new Button("8");
		button9 = new Button("9");
		rowCenterNumber.appendChild(button0);
		rowCenterNumber.appendChild(button1);
		rowCenterNumber.appendChild(button2);
		rowCenterNumber.appendChild(button3);
		rowCenterNumber.appendChild(button4);
		rowCenterNumber.appendChild(button5);
		rowCenterNumber.appendChild(button6);
		rowCenterNumber.appendChild(button7);
		rowCenterNumber.appendChild(button8);
		rowCenterNumber.appendChild(button9);
		
		
		Grid gridCenterLetters= new Grid();
		divCenter.appendChild(gridCenterLetters);	
		Rows rowsCenterLetters = gridCenterLetters.newRows();
		
		Row rowCenterLetters = rowsCenterLetters.newRow();		
		rowCenterLetters.setStyle(styleRowLetter);
	
	
		buttonA = new Button("A");
		buttonA.setStyle(styleButtonLetter);
		buttonB = new Button("B");
		buttonB.setStyle(styleButtonLetter);
		buttonC = new Button("C");
		buttonC.setStyle(styleButtonLetter);
		buttonD = new Button("D");
		buttonD.setStyle(styleButtonLetter);
		buttonE = new Button("E");
		buttonE.setStyle(styleButtonLetter);
		buttonF = new Button("F");
		buttonF.setStyle(styleButtonLetter);
		buttonG = new Button("G");
		buttonG.setStyle(styleButtonLetter);
		buttonH = new Button("H");
		buttonH.setStyle(styleButtonLetter);
		rowCenterLetters.appendChild(buttonA);
		rowCenterLetters.appendChild(buttonB);
		rowCenterLetters.appendChild(buttonC);
		rowCenterLetters.appendChild(buttonD);
		rowCenterLetters.appendChild(buttonE);
		rowCenterLetters.appendChild(buttonF);
		rowCenterLetters.appendChild(buttonG);
		rowCenterLetters.appendChild(buttonH);
		
		
		rowCenterLetters = rowsCenterLetters.newRow();
		rowCenterLetters.setStyle(styleRowLetter);
		buttonI = new Button("I");
		buttonI.setStyle(styleButtonLetter);
		buttonJ = new Button("J");
		buttonJ.setStyle(styleButtonLetter);
		buttonK = new Button("K");
		buttonK.setStyle(styleButtonLetter);
		buttonL = new Button("L");
		buttonL.setStyle(styleButtonLetter);
		buttonM = new Button("M");
		buttonM.setStyle(styleButtonLetter);
		buttonN = new Button("N");
		buttonN.setStyle(styleButtonLetter);
		buttonO = new Button("O");
		buttonO.setStyle(styleButtonLetter);
		buttonP = new Button("P");
		buttonP.setStyle(styleButtonLetter);
		rowCenterLetters.appendChild(buttonI);
		rowCenterLetters.appendChild(buttonJ);
		rowCenterLetters.appendChild(buttonK);
		rowCenterLetters.appendChild(buttonL);
		rowCenterLetters.appendChild(buttonM);
		rowCenterLetters.appendChild(buttonN);
		rowCenterLetters.appendChild(buttonO);
		rowCenterLetters.appendChild(buttonP);

		rowCenterLetters = rowsCenterLetters.newRow();
		rowCenterLetters.setStyle(styleRowLetter);
		
		buttonQ = new Button("Q");
		buttonQ.setStyle(styleButtonLetter);
		buttonR = new Button("R");
		buttonR.setStyle(styleButtonLetter);
		buttonS = new Button("S");
		buttonS.setStyle(styleButtonLetter);
		buttonT = new Button("T");
		buttonT.setStyle(styleButtonLetter);
		buttonU = new Button("U");
		buttonU.setStyle(styleButtonLetter);
		buttonV = new Button("V");
		buttonV.setStyle(styleButtonLetter);
		buttonW = new Button("W");
		buttonW.setStyle(styleButtonLetter);
		buttonX = new Button("X");
		buttonX.setStyle(styleButtonLetter);
		rowCenterLetters.appendChild(buttonQ);
		rowCenterLetters.appendChild(buttonR);
		rowCenterLetters.appendChild(buttonS);
		rowCenterLetters.appendChild(buttonT);
		rowCenterLetters.appendChild(buttonU);
		rowCenterLetters.appendChild(buttonV);
		rowCenterLetters.appendChild(buttonW);
		rowCenterLetters.appendChild(buttonX);

		rowCenterLetters = rowsCenterLetters.newRow();
		rowCenterLetters.setStyle(styleRowLetter);
		buttonY = new Button("Y");
		buttonY.setStyle(styleButtonLetter);
		buttonZ = new Button("Z");
		buttonZ.setStyle(styleButtonLetter);
		buttonAceptar = new Button("Aceptar");
		buttonAceptar.setStyle("width:100%; height:100%;font-size: 15px");
		buttonBorrar = new Button("Borrar");
		buttonBorrar.setStyle("width:100%; height:70px;font-size: 15px");
		buttonAnular = new Button("Anular de Exportable");
		buttonAnular.setStyle("width:100%; height:100%;font-size: 15px;");
		Space space = new Space();
		rowCenterLetters.appendChild(buttonY);
		rowCenterLetters.appendChild(buttonZ);
		rowCenterLetters.appendChild(space);
		rowCenterLetters.appendCellChild(buttonAceptar,2);
		rowCenterLetters.appendChild(buttonBorrar);
		rowCenterLetters.appendCellChild(buttonAnular,2);
		
		Grid gridCenterDown= new Grid();
		divCenter.appendChild(gridCenterDown);
		Rows rowsCenterDown = gridCenterDown.newRows();
		
		Row rowCenterDown = rowsCenterDown.newRow();
		buttonInv = new Button("Inventario");
//		buttonInv.setStyle("width:100%; height:100%;font-size: 15px");
		buttonOrden = new Button("Orden Fija");
		buttonImpresion = new Button("Impresión");
		buttonReemb = new Button("Reembonche");
		buttonBQTS = new Button("BQTS");
		rowCenterDown.appendCellChild(buttonInv,2);
		rowCenterDown.appendCellChild(buttonOrden,2);
		rowCenterDown.appendCellChild(buttonImpresion,2);
		rowCenterDown.appendCellChild(buttonReemb,2);
		rowCenterDown.appendChild(buttonBQTS);

	

		
		
		//************************ East********************************//
		
		East mainPanelEast = new East();
		mainPanelEast.setWidth("40%");
		mainLayout.appendChild(mainPanelEast);
		
		//************************East North *************************//
		Borderlayout layoutEast = new Borderlayout();
		mainPanelEast.appendChild(layoutEast);
		
		North northEeast = new North();
		layoutEast.appendChild(northEeast);
		
			Div divEast = new Div();
			northEeast.appendChild(divEast);
			
			Grid gridEast1 = new Grid();
			divEast.appendChild(gridEast1);
			Rows rowsEast1 = gridEast1.newRows();
			Row rowEast1 = rowsEast1.newRow();
			
			Label labelDayEast = new Label("DOMINGO");
			labelDayEast.setStyle("font-size:22px;font-weight: bold");
			rowEast1.appendChild(labelDayEast);
			
			Grid gridDays = new Grid();
			divEast.appendChild(gridDays);
			Rows rowsDays = gridDays.newRows();
			
			Row rowDays = rowsDays.newRow();
			buttonDo = new Button("Do");
			buttonLu = new Button("Lu");
			buttonMa = new Button("Ma");
			buttonMi = new Button("Mi");
			buttonJu = new Button("Ju");
			buttonVi = new Button("Vi");
			buttonSa = new Button("Sa");
		
			rowDays.appendChild(buttonDo);
			rowDays.appendChild(buttonLu);
			rowDays.appendChild(buttonMa);
			rowDays.appendChild(buttonMi);
			rowDays.appendChild(buttonJu);
			rowDays.appendChild(buttonVi);
			rowDays.appendChild(buttonSa);
			
		Center centerEast = new Center();
		layoutEast.appendChild(centerEast);
		
		
			 variedadTable = ListboxFactory.newDataTable();
			 variedadTable.setStyle("color:red");
			 variedadTable.addColumn(KeyNamePair.class, true, "Variedad");
			 variedadTable.repaint();
			 centerEast.appendChild(variedadTable);
			 addLineTable();
		
			 
			 ///////////////////AÑADIR EVENTOS////////////////

				button0.addEventListener(Events.ON_CLICK, this);
				button1.addEventListener(Events.ON_CLICK, this);
				button2.addEventListener(Events.ON_CLICK, this);
				button3.addEventListener(Events.ON_CLICK, this);
				button4.addEventListener(Events.ON_CLICK, this);
				button5.addEventListener(Events.ON_CLICK, this);
				button6.addEventListener(Events.ON_CLICK, this);
				button7.addEventListener(Events.ON_CLICK, this);
				button8.addEventListener(Events.ON_CLICK, this);

				buttonA.addEventListener(Events.ON_CLICK, this);
				buttonB.addEventListener(Events.ON_CLICK, this);
				buttonC.addEventListener(Events.ON_CLICK, this);
				buttonD.addEventListener(Events.ON_CLICK, this);
				buttonE.addEventListener(Events.ON_CLICK, this);
				buttonF.addEventListener(Events.ON_CLICK, this);
				buttonG.addEventListener(Events.ON_CLICK, this);
				buttonH.addEventListener(Events.ON_CLICK, this);
				buttonI.addEventListener(Events.ON_CLICK, this);
				buttonJ.addEventListener(Events.ON_CLICK, this);
				buttonK.addEventListener(Events.ON_CLICK, this);
				buttonL.addEventListener(Events.ON_CLICK, this);
				buttonM.addEventListener(Events.ON_CLICK, this);
				buttonN.addEventListener(Events.ON_CLICK, this);	
				buttonO.addEventListener(Events.ON_CLICK, this);
				buttonP.addEventListener(Events.ON_CLICK, this);
				buttonQ.addEventListener(Events.ON_CLICK, this);
				buttonR.addEventListener(Events.ON_CLICK, this);
				buttonS.addEventListener(Events.ON_CLICK, this);
				buttonT.addEventListener(Events.ON_CLICK, this);
				buttonU.addEventListener(Events.ON_CLICK, this);
				buttonV.addEventListener(Events.ON_CLICK, this);
				buttonW.addEventListener(Events.ON_CLICK, this);
				buttonX.addEventListener(Events.ON_CLICK, this);
				buttonY.addEventListener(Events.ON_CLICK, this);
				buttonZ.addEventListener(Events.ON_CLICK, this);

				buttonAceptar.addEventListener(Events.ON_CLICK, this);
				buttonBorrar.addEventListener(Events.ON_CLICK, this);
				buttonAnular.addEventListener(Events.ON_CLICK, this);

				buttonOrden.addEventListener(Events.ON_CLICK, this);
				buttonImpresion.addEventListener(Events.ON_CLICK, this);
				buttonReemb.addEventListener(Events.ON_CLICK, this);
				buttonBQTS.addEventListener(Events.ON_CLICK, this);
				
				buttonDo.addEventListener(Events.ON_CLICK, this);
				buttonLu.addEventListener(Events.ON_CLICK, this);
				buttonMa.addEventListener(Events.ON_CLICK, this);
				buttonMi.addEventListener(Events.ON_CLICK, this);
				buttonJu.addEventListener(Events.ON_CLICK, this);
				buttonVi.addEventListener(Events.ON_CLICK, this);
				buttonSa.addEventListener(Events.ON_CLICK, this);
				variedadTable.addEventListener(Events.ON_SELECT, this);

		
		
	}


	@Override
	public void onEvent(Event event) throws Exception {
		if (event.getTarget().equals(buttonA) || event.getTarget().equals(buttonB) || event.getTarget().equals(buttonC) || event.getTarget().equals(buttonD) 
		 || event.getTarget().equals(buttonD) || event.getTarget().equals(buttonE) || event.getTarget().equals(buttonF) || event.getTarget().equals(buttonG) 
	     || event.getTarget().equals(buttonH) || event.getTarget().equals(buttonI) || event.getTarget().equals(buttonJ) || event.getTarget().equals(buttonK) || event.getTarget().equals(buttonL)
	     || event.getTarget().equals(buttonM) || event.getTarget().equals(buttonN) || event.getTarget().equals(buttonO) || event.getTarget().equals(buttonP)
	     || event.getTarget().equals(buttonQ) || event.getTarget().equals(buttonR) || event.getTarget().equals(buttonS) || event.getTarget().equals(buttonT)
	     || event.getTarget().equals(buttonU) || event.getTarget().equals(buttonV) || event.getTarget().equals(buttonW) || event.getTarget().equals(buttonX)
	     || event.getTarget().equals(buttonY) || event.getTarget().equals(buttonZ)){
		 Button button = (Button) event.getTarget();
		 searchVariedad(button.getLabel());
		
		}else if (event.getTarget().equals(variedadTable)){
			
		}
	}
	public void searchVariedad(String letter){
		List<MProduct> product = new Query(Env.getCtx(), MProduct.Table_Name, "name like '"+letter+"%' ", null).setOrderBy("name").list();
        Vector<Vector<Object>> linesDataVariedadTable = new Vector<Vector<Object>>();
        variedadTable.clearTable();
        
        for (int i = 0; i < product.size(); i++) {
           	Vector<Object> line = new Vector<Object>();
           	linesDataVariedadTable.add(line);
           	line.add(product.get(i).getName());	
		}

       	
	    ListModelTable variedadTableModelToProcess = new ListModelTable(linesDataVariedadTable);
		variedadTableModelToProcess.addTableModelListener(this);
		variedadTable.setModel(variedadTableModelToProcess);
		variedadTable.repaint();
	
	}

	public void addLineTable(){

	}

	@Override
	public ADForm getForm() {
		return form;
	}

	@Override
	public void tableChanged(WTableModelEvent event) {
		System.out.println(event);
		
	}
}
