package slidenerd.vivz.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import slidenerd.vivz.bucketdrops.beans.Drop;

/**
 * Created by on 11.04.2016.
 */
public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mImputWhat;
    private DatePicker mImputWhen;
    private Button mBtnAdd;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_add_it:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    private void addAction() {
        String what = mImputWhat.getText().toString();
        long now = System.currentTimeMillis();
        //Create Default Realm Configuration
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getActivity()).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm realm = Realm.getDefaultInstance();
        //Create a Drop Object to store as an Object in the Realm Database
        Drop drop = new Drop(what, now, 0, false);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();
    }

    public DialogAdd() {
    }

    //Convert the XML dialog_add in a java objekt, with the infator
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    //TODO add date to database object

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mBtnAdd = (Button) view.findViewById(R.id.btn_add);
        mImputWhat = (EditText) view.findViewById(R.id.et_drop);
        mImputWhen = (DatePicker) view.findViewById(R.id.bpv_date);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);

        mBtnClose.setOnClickListener(mBtnClickListener);
        //mBtnAdd.setOnClickListener(mBtnClickListener);
    }
}
