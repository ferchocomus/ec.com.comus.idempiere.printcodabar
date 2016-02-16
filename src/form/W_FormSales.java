package form;


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.MFlCodabar;
import model.X_FLC_CodaBar;

import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Combobox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.event.WTableModelEvent;
import org.adempiere.webui.event.WTableModelListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MInventory;
import org.compiere.model.MInventoryLine;
import org.compiere.model.MLocator;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.MQuery;
import org.compiere.model.MSysConfig;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.PrintInfo;
import org.compiere.model.Query;
import org.compiere.model.SystemIDs;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportCtl;
import org.compiere.print.ReportEngine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.North;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;




public class W_FormSales implements IFormController, EventListener<Event>, WTableModelListener ,ValueChangeListener
{

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
	private WStringEditor wsVariedad;
	private WTableDirEditor wlTipoBunch;
	private Label labelDayEast;
	private WTableDirEditor wtLongitud;
	private WTableDirEditor wtTallos;
	private WStringEditor wsMesas;
	private WStringEditor wsEtiquetas;
	private String number ="";
//	private Textbox stringEditor = new Textbox();
	private int idLocator;
	private Date date;
	private PO variedadSelected;
	private int m_M_AttributeSet_ID;
//	private Combobox listEditor;
	private String fieldFocus;
private WTableDirEditor wtOrg;

	public W_FormSales(){
		try {
			zkInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
        m_M_AttributeSet_ID = MSysConfig.getIntValue("FLC_DateM_AttributeSet_ID", 0); 
		
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
		
		String styleLabel = "font-size:15px;font-weight: bold";
		Row rowCenter = rowsCenter.newRow();		
		Label lblVariedad = new Label("Variedad");
		lblVariedad.setStyle(styleLabel);
		Label lblFinca = new Label("Finca");
		lblFinca.setStyle(styleLabel);
		
		rowCenter.appendChild(lblVariedad);
		rowCenter.appendChild(lblFinca);
		
		rowCenter = rowsCenter.newRow();
	    wsVariedad = new WStringEditor();
		wsVariedad.getComponent().setHeight("18px");
		wsVariedad.getComponent().setWidth("90%");
		wsVariedad.setReadWrite(false);
		wsVariedad.getComponent().setStyle(styleLabel);
	    MLookup lookupOrg = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.COLUMN_C_PERIOD_AD_ORG_ID, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_Org_ID", 0, false, "(AD_Org.AD_Org_ID != 0 AND AD_Org.IsSummary = 'N')");
	    wtOrg = new WTableDirEditor("AD_Org_ID", true, false, true, lookupOrg);
	    wtOrg.getComponent().setStyle(styleLabel);
	    wtOrg.setValue(Env.getAD_Org_ID(Env.getCtx()));
		rowCenter.appendChild(wsVariedad.getComponent());
		rowCenter.appendChild(wtOrg.getComponent());
		
		
		Grid gridCenter2 = new Grid();
		divCenter.appendChild(gridCenter2);
		Rows rowsCenter2 = gridCenter2.newRows();
		
		Row rowCenter2 = rowsCenter2.newRow();
		Label lblTipoBunch = new Label("Tipo de Bunch");
		lblTipoBunch.setStyle(styleLabel);
		Label lblLongitud = new Label("Longitud");	
		lblLongitud.setStyle(styleLabel);
		Label lblTallos = new Label("Tallos");	
		lblTallos.setStyle(styleLabel);
		Label lblMesas = new Label("Mesa");
		lblMesas.setStyle(styleLabel);
		Label lblEtiquetas = new Label("Etiquetas");
		lblEtiquetas.setStyle(styleLabel);
		rowCenter2.appendCellChild(lblTipoBunch,2);
		rowCenter2.appendChild(lblLongitud);
		rowCenter2.appendChild(lblTallos);
		rowCenter2.appendChild(lblMesas);
		rowCenter2.appendChild(lblEtiquetas);
		
		rowCenter2 = rowsCenter2.newRow();
		rowCenter2.setStyle("height:30px");

		
//		wlTipoBunch = new WLocatorEditor ("M_Locator_ID", true, false, true, locator, form.getWindowNo());
		MLookup lookupLocator = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.REFERENCE_DATATYPE_LOCATOR, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "M_Locator_ID", 0, false, "(Isactive = 'Y')");
		wlTipoBunch = new WTableDirEditor("M_Locator_ID", true, false, true, lookupLocator);
		wlTipoBunch.getComponent().setStyle(styleLabel);
		
		MLookup lookupLongitud = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.REFERENCE_DATATYPE_LIST, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_Ref_List_ID", 0, false, "(AD_Reference_ID=1000014)");	
		wtLongitud = new WTableDirEditor("AD_Ref_List_ID", true, false, true, lookupLongitud);
		wtLongitud.getComponent().setWidth("70px");
		wtLongitud.getComponent().setStyle(styleLabel);
		
		MLookup lookupTallos = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), SystemIDs.REFERENCE_DATATYPE_LIST, DisplayType.TableDir, Env.getLanguage(Env.getCtx()), "AD_Ref_List_ID", 0, false, "(AD_Reference_ID=1000017)");
		wtTallos = new WTableDirEditor("AD_Ref_List_ID", true, false, true, lookupTallos);
		wtTallos.getComponent().setWidth("70px");
		wtTallos.getComponent().setStyle(styleLabel);
		wtTallos.setMandatory(true);
		
	    wsMesas = new WStringEditor();
	    wsMesas.getComponent().setHeight("18px");
	    wsMesas.getComponent().setWidth("50%");
	    wsMesas.getComponent().setStyle(styleLabel);
	    wsMesas.setMandatory(true);
	    wsEtiquetas = new WStringEditor();
	    wsEtiquetas.getComponent().setHeight("18px");
	    wsEtiquetas.getComponent().setWidth("50%");
	    wsEtiquetas.getComponent().setStyle(styleLabel);
	    wsEtiquetas.setMandatory(true);
		rowCenter2.appendCellChild(wlTipoBunch.getComponent(),2);
		rowCenter2.appendChild(wtLongitud.getComponent());
		rowCenter2.appendChild(wtTallos.getComponent());
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
		String styleButtonNumber = "width:40; height:40;font-size: 20px;font-style: italic; color:blue";
		
		Row rowCenterNumber = rowsCenterNumbers.newRow();
		rowCenterNumber.setStyle("height:60");
		button0 = new Button("0");
		button0.setStyle(styleButtonNumber);
		button1 = new Button("1");
		button1.setStyle(styleButtonNumber);
		button2 = new Button("2");
		button2.setStyle(styleButtonNumber);
		button3 = new Button("3");
		button3.setStyle(styleButtonNumber);
		button4 = new Button("4");
		button4.setStyle(styleButtonNumber);
		button5 = new Button("5");
		button5.setStyle(styleButtonNumber);
		button6 = new Button("6");
		button6.setStyle(styleButtonNumber);
		button7 = new Button("7");
		button7.setStyle(styleButtonNumber);
		button8 = new Button("8");
		button8.setStyle(styleButtonNumber);
		button9 = new Button("9");
		button9.setStyle(styleButtonNumber);
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
			
		    labelDayEast = new Label();
		    setDay(-1);
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
			 variedadTable.repaint();
			 centerEast.appendChild(variedadTable);
		
			 
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
				button9.addEventListener(Events.ON_CLICK, this);

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
				variedadTable.addEventListener(Events.ON_CLICK, this);
				wlTipoBunch.addValueChangeListener(this);
				
				wsMesas.getComponent().addEventListener(Events.ON_FOCUS, this);
				wsEtiquetas.getComponent().addEventListener(Events.ON_FOCUS, this);
				wtLongitud.getComponent().addEventListener(Events.ON_FOCUS, this);
				wtLongitud.addValueChangeListener(this);
				

		
		
	}


	@Override
	public void onEvent(Event event) throws Exception {
		if(event.getName().equals(Events.ON_FOCUS)){
			if (event.getTarget().equals(wsMesas.getComponent()))
				fieldFocus = "wsMesas";
			else if (event.getTarget().equals(wsEtiquetas.getComponent()))
				fieldFocus = "wsEtiquetas";
			else if (event.getTarget().equals(wtLongitud.getComponent()))
				fieldFocus = "wtLongitud";

			
		}else if (event.getTarget().equals(buttonA) || event.getTarget().equals(buttonB) || event.getTarget().equals(buttonC) || event.getTarget().equals(buttonD) 
		 || event.getTarget().equals(buttonD) || event.getTarget().equals(buttonE) || event.getTarget().equals(buttonF) || event.getTarget().equals(buttonG) 
	     || event.getTarget().equals(buttonH) || event.getTarget().equals(buttonI) || event.getTarget().equals(buttonJ) || event.getTarget().equals(buttonK) || event.getTarget().equals(buttonL)
	     || event.getTarget().equals(buttonM) || event.getTarget().equals(buttonN) || event.getTarget().equals(buttonO) || event.getTarget().equals(buttonP)
	     || event.getTarget().equals(buttonQ) || event.getTarget().equals(buttonR) || event.getTarget().equals(buttonS) || event.getTarget().equals(buttonT)
	     || event.getTarget().equals(buttonU) || event.getTarget().equals(buttonV) || event.getTarget().equals(buttonW) || event.getTarget().equals(buttonX)
	     || event.getTarget().equals(buttonY) || event.getTarget().equals(buttonZ)){
		 Button button = (Button) event.getTarget();
		 searchVariedad(button.getLabel());
		
		}else if (event.getTarget().equals(variedadTable)){
			 WListbox listBox = (WListbox) event.getTarget();
			 if (listBox.getSelectedItem()!=null){
				 String nameVariedad = listBox.getSelectedItem().getLabel();
				 wsVariedad.setValue(nameVariedad);
				 variedadSelected = new Query(Env.getCtx(), MProduct.Table_Name, "name = ?",null).setParameters(nameVariedad).first();
			 }
			
		}else if(event.getTarget().equals(buttonDo) || event.getTarget().equals(buttonLu) || event.getTarget().equals(buttonMa) || event.getTarget().equals(buttonMi)
				|| event.getTarget().equals(buttonJu) || event.getTarget().equals(buttonVi) || event.getTarget().equals(buttonSa)){
			 Button button = (Button) event.getTarget();
			 String day = button.getLabel();
			 if (day.equals("Do"))
				 setDay(1);
			 else if (day.equals("Lu"))
				 setDay(2);
			 else if (day.equals("Ma"))
				 setDay(3);
			 else if (day.equals("Mi"))
				 setDay(4);
			 else if (day.equals("Ju"))
				 setDay(5);
			 else if (day.equals("Vi"))
				 setDay(6);
			 else if (day.equals("Sa"))
				 setDay(7);	
		}else if(event.getTarget().equals(button0) || event.getTarget().equals(button1) || event.getTarget().equals(button2) || event.getTarget().equals(button3) || event.getTarget().equals(button4)
				 || event.getTarget().equals(button5) || event.getTarget().equals(button6) || event.getTarget().equals(button7)
				 || event.getTarget().equals(button8) || event.getTarget().equals(button9)){
			 Button button = (Button) event.getTarget();
			 String numberButton = button.getLabel();

			 if (fieldFocus.equals("wsMesas")){
				 number = wsMesas.getDisplay() + numberButton;
			 	 wsMesas.getComponent().setText(number);
			 } else if (fieldFocus.equals("wsEtiquetas")){
				 number = wsEtiquetas.getDisplay() + numberButton;
				 wsEtiquetas.getComponent().setText(number);
			 }			 
			 	
		}else if(event.getTarget().equals(buttonBorrar)){
			if (fieldFocus.equals("wtLongitud")){
				wtLongitud.setValue("");
			}else if(fieldFocus.equals("wsMesas") && !wsMesas.getComponent().getText().equals(""))
					wsMesas.getComponent().setText(wsMesas.getComponent().getText().substring(0, wsMesas.getComponent().getText().length()-1));
				else if(fieldFocus.equals("wsEtiquetas") && !wsEtiquetas.getComponent().getText().equals(""))
					wsEtiquetas.getComponent().setText(wsEtiquetas.getComponent().getText().substring(0, wsEtiquetas.getComponent().getText().length()-1));
			
		}else if(event.getTarget().equals(buttonAceptar)){
			registrar();	
			
		}else if (event.getTarget().equals(buttonImpresion)){
//			imprimir(1000001);
		}
	}
	

	public void setDay(int daySelected){
		
		Calendar cal = Calendar.getInstance();
		if (daySelected>0){
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			int sumDays = daySelected-dayOfWeek;
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DATE)+sumDays);
		}
		date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String date1 = format.format(date); 

		String[] strDays = new String[]{
				"DOMINGO",
				"LUNES",
				"MARTES",
				"MIERCOLES",
				"JUEVES",
				"VIERNES",
				"SABADO"};

			
		String dia = strDays[cal.get(Calendar.DAY_OF_WEEK)-1];
		labelDayEast.setValue(dia+" "+date1);
	}
	
	public void searchVariedad(String letter){
		String whereLongitud  = wtLongitud.getDisplay()==null?"":" and classification like  '%"+wtLongitud.getDisplay().trim()+"%'";
		List<MProduct> product = new Query(Env.getCtx(), MProduct.Table_Name, "name like '"+letter+"%' "+whereLongitud, null).setOrderBy("name").list();
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
		variedadTable.setHflex("100");
		variedadTable.repaint();
	
	}

	@Override
	public ADForm getForm() {
		return form;
	}

	@Override
	public void tableChanged(WTableModelEvent event) {
		
	}
	
	public boolean validar(){
		if (wtTallos.getDisplay()==null){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe seleccionar un tallo");
			return false;
		}
		if (wsEtiquetas.getDisplay()==null || wsEtiquetas.getDisplay().isEmpty()){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe llenar el campo Etiqueta");
			return false;
		}
		if (wsMesas.getDisplay()==null || wsMesas.getDisplay().isEmpty()){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe llenar el campo Mesa");
			return false;
		}
		if (wtOrg.getDisplay()==null){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe seleccionar una finca");
			return false;
		}
		if (wsVariedad.getDisplay().isEmpty()){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe seleccionar una Variedad");
			return false;
		}
		if (wlTipoBunch.getDisplay() == null){
			FDialog.error(form.getWindowNo(), form, "Error", "Debe seleccionar un Tipo de Bunch");
			return false;
		}
		
		
		return true;
		
	}
	public void registrar()
	{
		if(!validar())
			return;
		Properties m_ctx = Env.getCtx();
		MLocator mLocator = new MLocator(m_ctx,idLocator, null);
		int m_AD_Org_ID = (int) wtOrg.getValue();
	    int m_AD_Client_ID = mLocator.getAD_Client_ID();
		MWarehouse mWarehouse = new MWarehouse(Env.getCtx(), mLocator.get_ID(), null);    	
		int idProyecto = 1000000;
		int idCampaign = 1000000;
//		Trx t = Trx.get(Trx.createTrxName("trx_ccb"), true);
		BigDecimal cantEtiquetas = new BigDecimal (wsEtiquetas.getValue().toString().trim());
		BigDecimal tallos = new BigDecimal (wtTallos.getDisplay().trim());
    	String tmstmp = new Integer(1900+this.date.getYear()).toString();
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(date);
    	Integer mes = new Integer(1+date.getMonth());
    	Integer dia = new Integer(cal.get(Calendar.DAY_OF_MONTH));
    	tmstmp += "-"+((mes<10)?"0"+mes:mes)+"-"+((dia<10)?"0"+dia:dia)+" 00:00:00";
    	int MAttributeSetInstanceId = getMAttributeSetInstanceId(m_M_AttributeSet_ID,java.sql.Timestamp.valueOf(tmstmp).toString().substring(0, 10));
    	MAttributeSetInstance asi = new MAttributeSetInstance(m_ctx,MAttributeSetInstanceId,null);
			    	
		MInventory inv = new MInventory(m_ctx,0,null);
		inv.setClientOrg(m_AD_Client_ID, m_AD_Org_ID);
		inv.setAD_Org_ID(m_AD_Org_ID);
		inv.setDescription("Ingreso de Flor Procesada :" + date.getTime());
		inv.setM_Warehouse_ID(mWarehouse.get_ID());
		inv.setMovementDate(new Timestamp(date.getTime()));
		inv.setC_DocType_ID(1000045);  //Inventario fisico phys inventory
		inv.setC_Project_ID(idProyecto);
		inv.setC_Campaign_ID(idCampaign);
		inv.setDocStatus("DR");
		inv.saveEx();
		
		System.out.println(inv.getDocumentInfo());

		MInventoryLine invl = new MInventoryLine(m_ctx,0,null);
		invl.setAD_Org_ID(m_AD_Org_ID);
		invl.setM_Inventory_ID(inv.getM_Inventory_ID());
		invl.setLine(10);
		invl.setM_Locator_ID(idLocator);
		invl.setM_Product_ID(variedadSelected.get_ID());
		invl.setQtyInternalUse(cantEtiquetas.multiply(tallos).negate());
		invl.setM_AttributeSetInstance_ID(MAttributeSetInstanceId==0?asi.get_ID():MAttributeSetInstanceId);
		invl.setQtyInternalUse(new BigDecimal(wsMesas.getValue().toString()));
		invl.setC_Charge_ID(1000009);  //Cargo Produccion de Flores
		invl.setM_Locator_ID(idLocator);
		invl.saveEx();
				        	
		inv.prepareIt();
		inv.approveIt();
		inv.completeIt();
		inv.setProcessed(true);
		inv.setDocStatus("CO");
		inv.saveEx();

		MFlCodabar code = new MFlCodabar(m_ctx, 0, null);

		code.setQty(cantEtiquetas);
		code.setUnitsPerPack(tallos.intValue()); //code.set_CustomColumn("txb_id", idBunch); 
        code.set_CustomColumn("mesa", wsMesas.getValue().toString());
		code.setC_Project_ID(idProyecto);
		code.setC_Campaign_ID(idCampaign);
		code.setM_Product_ID(variedadSelected.get_ID());
		code.setAD_Org_ID(m_AD_Org_ID);
		code.setM_Locator_ID(idLocator);
		code.setM_Inventory_ID(inv.get_ID());
   		code.saveEx();
		code.setUPC(String.valueOf(code.get_ID()));
		code.saveEx();
		
   		System.out.println(code.get_ID());
   		imprimir(code.get_ID());
	}//imprimir
	
	public void imprimir(int idFlcCodaBar){

	  MQuery query = new MQuery("flc_codabar");
	  query.addRestriction("flc_codabar_id", MQuery.EQUAL, idFlcCodaBar);
	  MPrintFormat format = MPrintFormat.get (Env.getCtx(), 1000062, false);
	  PrintInfo info = new PrintInfo("Codabar",X_FLC_CodaBar.Table_ID,idFlcCodaBar);
	  ReportEngine re = new ReportEngine(Env.getCtx(), format, query, info);
	  re.setWindowNo(form.getWindowNo());
	  ReportCtl.preview(re);
	  
//	  Clients.clearBusy();
//		try {
//			Window win = new SimplePDFViewer(form.getFormName(), new FileInputStream(re.getPDF()));
//			SessionManager.getAppDesktop().showWindow(win, "center");
//		} catch (Exception e)
//		{
//			
//		}
		
	}
	///fercho
	public static int getMAttributeSetInstanceId(int m_attrinuteset_id, String guarantedate){	
    	int returnValue=0;
    	int VLSValue=0;
    	 String consulta = "SELECT coalesce(max(at.m_attributesetinstance_id),0) as M_AttributeSetinstance_ID " +
         		" FROM ADEMPIERE.m_attributesetinstance at " +
         		" WHERE  " +
         		"  at.m_attributeset_id = " +m_attrinuteset_id+
         		" AND at.guaranteedate    = '" +guarantedate+
         		"'  AND at.ad_client_id         = " + Env.getAD_Client_ID(Env.getCtx()) +
       			"AND at.isactive        = 'Y' " ;

         try {
            PreparedStatement pstmt = DB.prepareStatement(consulta, null);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	VLSValue=rs.getInt("M_AttributeSetinstance_ID");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(W_FormSales.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(VLSValue>0)
        	return VLSValue;
        else 
        	return returnValue;
     }

	@Override
	public void valueChange(ValueChangeEvent evt) {
		String name = evt.getPropertyName();
		Object value = evt.getNewValue();
		if (value ==null)
			return;
		
		if (name.equals("M_Locator_ID")){
			idLocator = Integer.parseInt(evt.getNewValue().toString());
		}else if (name.equals("AD_Ref_List_ID")){
			fieldFocus = "wtLongitud";
		}
		
	}
	
}
