package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jimga150
 */
public final class SeasonOfGathering extends CardImpl {

    public SeasonOfGathering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");
        

        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        // {P} -- Put a +1/+1 counter on a creature you control. It gains vigilance and trample until end of turn.
        // {P}{P} -- Choose artifact or enchantment. Destroy all permanents of the chosen type.
        // {P}{P}{P} -- Draw cards equal to the greatest power among creatures you control.
    }

    private SeasonOfGathering(final SeasonOfGathering card) {
        super(card);
    }

    @Override
    public SeasonOfGathering copy() {
        return new SeasonOfGathering(this);
    }
}
