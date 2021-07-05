
package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Raphael-Schulz
 */
public final class DemogorgonsClutches extends CardImpl {

    public DemogorgonsClutches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent discards two cards, mills two cards, and loses 2 life.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));

        Effect effect = new MillCardsTargetEffect(2);
        effect.setText(", mills two cards");
        this.getSpellAbility().addEffect(effect);

        effect = new LoseLifeTargetEffect(2);
        effect.setText(", and loses 2 life");
        this.getSpellAbility().addEffect(effect);

        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private DemogorgonsClutches(final DemogorgonsClutches card) {
        super(card);
    }

    @Override
    public DemogorgonsClutches copy() {
        return new DemogorgonsClutches(this);
    }
}
