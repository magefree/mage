
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.permanent.token.BreedingPitThrullToken;

/**
 *
 * @author jeffwadsworth
 */
public final class BreedingPit extends CardImpl {

    public BreedingPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // At the beginning of your upkeep, sacrifice Breeding Pit unless you pay {B}{B}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{B}{B}")), TargetController.YOU, false));

        // At the beginning of your end step, create a 0/1 black Thrull creature token.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new BreedingPitThrullToken()), false));
    }

    private BreedingPit(final BreedingPit card) {
        super(card);
    }

    @Override
    public BreedingPit copy() {
        return new BreedingPit(this);
    }
}
