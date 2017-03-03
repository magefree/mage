package mage.client.util;

import mage.client.deckeditor.table.CardHelper;
import mage.constants.CardType;
import mage.view.CardView;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created by IGOUDT on 3-3-2017.
 */
public class CardHelperTest {

    @Test
    public void testCardTypeOrder() {
        CardView v = new CardView(true);
        v.getCardTypes().add(CardType.CREATURE);
        v.getCardTypes().add(CardType.ARTIFACT);
        String cardtypeText = CardHelper.getType(v);
        Assert.assertThat(cardtypeText, is("Artifact Creature"));

    }


}
