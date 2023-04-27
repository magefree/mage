
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class Vivify extends CardImpl {

    public Vivify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Target land becomes a 3/3 creature until end of turn. It's still a land.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(3, 3), false, true, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Vivify(final Vivify card) {
        super(card);
    }

    @Override
    public Vivify copy() {
        return new Vivify(this);
    }
}
