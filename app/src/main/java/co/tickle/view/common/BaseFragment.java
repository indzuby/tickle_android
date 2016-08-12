package co.tickle.view.common;

import android.support.v4.app.Fragment;
import android.view.View;

import co.tickle.utils.CodeDefinition;
import co.tickle.utils.SessionUtils;

/**
 * Created by zuby on 2016. 5. 9..
 */
public class BaseFragment extends Fragment implements View.OnClickListener{
    public View mView;
    protected String token;


    @Override
    public void onClick(View v) {

    }
    public void init(){
        token = SessionUtils.getString(getContext(), CodeDefinition.TOKEN,"");
    }
}
