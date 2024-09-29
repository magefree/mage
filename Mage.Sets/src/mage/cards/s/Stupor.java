package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class Stupor extends CardImpl {

    public Stupor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Target opponent discards a card at random, then discards a card.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1).setText(", then discards a card"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Stupor(final Stupor card) {
        super(card);
    }

    @Override
    public Stupor copy() {
        return new Stupor(this);
    }
}
