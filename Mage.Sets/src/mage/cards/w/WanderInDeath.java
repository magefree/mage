
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class WanderInDeath extends CardImpl {

    public WanderInDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return up to two target creature cards from your graveyard to your hand.
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, new FilterCreatureCard("creature cards from your graveyard")));
        getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public WanderInDeath(final WanderInDeath card) {
        super(card);
    }

    @Override
    public WanderInDeath copy() {
        return new WanderInDeath(this);
    }
}
