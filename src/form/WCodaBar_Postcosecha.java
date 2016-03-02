package form;

import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;

public class WCodaBar_Postcosecha  extends CodaBarMain implements IFormController{

	@Override
	public ADForm getForm() {
		return getADForm();
	}

	public WCodaBar_Postcosecha(){
		try {
			zkInit(CodaBarType.POSTCOSECHA);
			loadValueVariable();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	};
}