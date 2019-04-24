
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.permanent.token.CarrionBlackInsectToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class Carrion extends CardImpl {

    public Carrion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // As an additional cost to cast Carrion, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Put X 0/1 black Insect creature tokens onto the battlefield, where X is the sacrificed creature's power.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CarrionBlackInsectToken(), new SacrificeCostCreaturesPower()));
    }

    public Carrion(final Carrion card) {
        super(card);
    }

    @Override
    public Carrion copy() {
        return new Carrion(this);
    }
}
