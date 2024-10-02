package mage.collation;

import mage.util.RandomUtil;

/**
 * @author TheElk801
 */
public class CardRun extends Rotater<String> {
    private int stripeLen=0,stripeWidth,stripeDepth;

    public CardRun(boolean keepOrder, String... numbers) {
        super(keepOrder, numbers);
    }

    public CardRun(int sLen, String... numbers) {
        super(true, numbers);
        stripeLen= sLen;
        stripeWidth= nextWidth();
        stripeDepth= 1+ RandomUtil.nextInt( stripeWidth );
    }

    // randomly choose a stripe width between 2 & 5 (inclusive)
    // ToDo: when data available, use different weightings for different widths
    private int nextWidth() {
        return 2+ RandomUtil.nextInt(4);
    }
    
    public int iterate() {
        if( stripeLen ==0 ){
          return super.iterate();
        if( stripeDepth < stripeWidth ){
          ++stripeDepth;
          return super.iterate(-stripeLen);
        }
        stripeDepth= 1;
        if this.isEdge(stripeLen){
          this.stripeWidth= this.nextWidth();
          return super.iterate(-1);
        }
        return super.iterate((stripeLen * (stripeWidth-1)) -1);
    }
}
