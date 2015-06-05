package thanhcs.blogspot.com.draganddrop;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@SuppressLint("NewApi") public class MainActivity extends Activity implements OnDragListener, View.OnLongClickListener {

    private static final String TAG = "junk";
    String mWord;
    TextView tvWord;
    ImageView imgAnswer ;
    ImageView img1, img2, img3;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWord = "2";
        tvWord =  (TextView)findViewById(R.id.tvwordba);
        tvWord.setText(mWord);

        imgAnswer = (ImageView) findViewById(R.id.imganswer);
        //        register a long click listener for the balls
        img1 = (ImageView)findViewById(R.id.img1);
        img1.setOnLongClickListener(this);
        img2 = (ImageView)findViewById(R.id.img2);
        img2.setOnLongClickListener(this);
        img3 = (ImageView)findViewById(R.id.img3);
        img3.setOnLongClickListener(this);
        img2.setOnLongClickListener(this);
        img3.setOnLongClickListener(this);
    //    nextQuestion();
        img1.setTag("1");
        img2.setTag(mWord);
        img3.setTag("3");
        findViewById(R.id.bottom_container).setTag(mWord);
        
        //        register drag event listeners for the target layout containers
        findViewById(R.id.top_container).setOnDragListener(this);
        findViewById(R.id.bottom_container).setTag(mWord);
        findViewById(R.id.bottom_container).setOnDragListener(this);
    }

    //    called when ball has been touched and held
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    public boolean onLongClick(View imageView) {
        //        the ball has been touched
        //            create clip data holding data of the type MIMETYPE_TEXT_PLAIN
        ClipData clipData = ClipData.newPlainText("", "");

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(imageView);
        /*start the drag - contains the data to be dragged,
            metadata for this data and callback for drawing shadow*/
        imageView.startDrag(clipData, shadowBuilder, imageView, 0);
        //        we're dragging the shadow so make the view invisible
        imageView.setVisibility(View.INVISIBLE);
        return true;
    }

    //    called when the ball starts to be dragged
    //    used by top and bottom layout containers
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    public boolean onDrag(View receivingLayoutView, DragEvent dragEvent) {
        View draggedImageView = (View) dragEvent.getLocalState();

        // Handles each of the expected events
        switch (dragEvent.getAction()) {

        case DragEvent.ACTION_DRAG_STARTED:
            Log.i(TAG, "drag action started");

            // Determines if this View can accept the dragged data
            if (dragEvent.getClipDescription()
                    .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                Log.i(TAG, "Can accept this data");

                // returns true to indicate that the View can accept the dragged data.
                return true;

            } else {
                Log.i(TAG, "Can not accept this data");

            }

            // Returns false. During the current drag and drop operation, this View will
            // not receive events again until ACTION_DRAG_ENDED is sent.
            return false;

        case DragEvent.ACTION_DRAG_ENTERED:
            Log.i(TAG, "drag action entered");
            //                the drag point has entered the bounding box
            return true;

        case DragEvent.ACTION_DRAG_LOCATION:
            Log.i(TAG, "drag action location");
            /*triggered after ACTION_DRAG_ENTERED
                stops after ACTION_DRAG_EXITED*/
            return true;

        case DragEvent.ACTION_DRAG_EXITED:
            Log.i(TAG, "drag action exited");
            //                the drag shadow has left the bounding box
            return true;

        case DragEvent.ACTION_DROP:
            /* the listener receives this action type when
                  drag shadow released over the target view
            the action only sent here if ACTION_DRAG_STARTED returned true
            return true if successfully handled the drop else false*/
            LinearLayout bottomLinearLayout = (LinearLayout) receivingLayoutView;
            switch (draggedImageView.getId()) {
            case R.id.img3:
                
                Log.wtf("taglagi", draggedImageView.getTag()+"");
                if(draggedImageView.getTag().equals(bottomLinearLayout.getTag()))
                {
                    ViewGroup draggedImageViewParentLayout
                    = (ViewGroup) draggedImageView.getParent();
                    draggedImageViewParentLayout.removeView(draggedImageView);

                    bottomLinearLayout.addView(draggedImageView);
                    draggedImageView.setVisibility(View.VISIBLE);
                    imgAnswer.setVisibility(View.GONE);
                    nextQuestion();
                }
                return false;

            case R.id.img1:
                Log.wtf("taglagi", draggedImageView.getTag()+"");
               
                if(draggedImageView.getTag().equals(bottomLinearLayout.getTag()))
                {
                    ViewGroup draggedImageViewParentLayout
                    = (ViewGroup) draggedImageView.getParent();
                    draggedImageViewParentLayout.removeView(draggedImageView);
                    bottomLinearLayout.addView(draggedImageView);
                    draggedImageView.setVisibility(View.VISIBLE);
                    imgAnswer.setVisibility(View.GONE);
                    nextQuestion();
                }
                return false;

            case R.id.img2:
                
               Log.wtf("taglagi", draggedImageView.getTag()+"");
                if(draggedImageView.getTag().equals(bottomLinearLayout.getTag()))
                {
                    ViewGroup draggedImageViewParentLayout
                    = (ViewGroup) draggedImageView.getParent();
                    draggedImageViewParentLayout.removeView(draggedImageView);

                    bottomLinearLayout.addView(draggedImageView);
                    draggedImageView.setVisibility(View.VISIBLE);
                    imgAnswer.setVisibility(View.GONE);
                    nextQuestion();
                }
                return false;
            default:

                Log.i(TAG, "in default");
                return false;
            }

        case DragEvent.ACTION_DRAG_ENDED:

            Log.i(TAG, "drag action ended");
            Log.i(TAG, "getResult: " + dragEvent.getResult());

            //                if the drop was not successful, set the ball to visible
            if (!dragEvent.getResult()) {
                Log.i(TAG, "setting visible");
                draggedImageView.setVisibility(View.VISIBLE);
            }

            return true;
            // An unknown action type was received.
        default:
            Log.i(TAG, "Unknown action type received by OnDragListener.");
            break;
        }
        return false;
    }

    private void nextQuestion() {

//        mWord = "Hand";
//        img1.setTag("lol");
//        img2.setTag(mWord);
//        img3.setTag("oll");
//        findViewById(R.id.bottom_container).setTag(mWord);

    }
}