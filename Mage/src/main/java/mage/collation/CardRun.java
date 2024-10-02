package mage.collation;

import mage.util.RandomUtil;

/**
 * @author TheElk801
 */
public class CardRun extends Rotater<String> {
    private int cardPos,stripeLen=0,stripeWidth,stripeDepth;
    // cardPos is used in place of private super.position for Striped Collation

    public CardRun(boolean keepOrder, String... numbers) {
        super(keepOrder, numbers);
    }

    public CardRun(int sLen, String... numbers) {
        super(true, numbers);
        cardPos= RandomUtil.nextInt( this.numItems() );
        stripeWidth= nextWidth();
        stripeDepth= 1+ RandomUtil.nextInt( stripeWidth );
        // assert sLen >0;
        // assert this.numItems() % sLen == 0;
    }

    private int nextWidth() {
        return 2+ RandomUtil.nextInt(4);
    }
    
    public int iterate() {
        if( stripeLen ==0 ){
          return super.iterate();
        }else{
          int i = cardPos;
          if( stripeDepth < stripeWidth ){
            ++stripeDepth;
            cardPos -= 10;
            if (cardPos <0 ){
              cardPos += this.numItems();
            }
          }else{
            stripeDepth= 1;
            if( (cardPos % stripeLen) >0 ){
              cardPos += stripeLen * (stripeWidth-1);
              cardPos %= this.numItems();
            }else{
              this.stripeWidth= this.nextWidth();
            }
            cardPos -= 1;
            if (cardPos <0 ){
              cardPos += this.numItems();
            }
          }
          return i;
        }
    }
}
