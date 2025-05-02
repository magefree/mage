package mage.cards.m;

import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author North
 */
public final class Mindcrank extends CardImpl {

    public Mindcrank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever an opponent loses life, that player mills that many cards.
        this.addAbility(new LoseLifeTriggeredAbility(
                new MillCardsTargetEffect(SavedLifeLossValue.MANY),
                TargetController.OPPONENT, false, true
        ));
    }

    private Mindcrank(final Mindcrank card) {
        super(card);
    }

    @Override
    public Mindcrank copy() {
        return new Mindcrank(this);
    }
}
