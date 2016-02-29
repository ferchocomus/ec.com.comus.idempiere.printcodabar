package form;

import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;


public class WCodaBar_National extends CodaBarMain implements IFormController{

	@Override
	public ADForm getForm() {
		return getADForm();
	}

	public WCodaBar_National(){
		try {
			zkInit(CodaBarType.NACIONAL);
			loadValueVariable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	};


}
