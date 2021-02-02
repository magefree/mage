
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PegasusToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class SacredMesa extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Pegasus");
    static {
        filter.add(SubType.PEGASUS.getPredicate());
    }

    public SacredMesa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // At the beginning of your upkeep, sacrifice Sacred Mesa unless you sacrifice a Pegasus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter))), TargetController.YOU, false));

        // {1}{W}: Create a 1/1 white Pegasus creature token with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PegasusToken()), new ManaCostsImpl<>("{1}{W}")));
    }

    private SacredMesa(final SacredMesa card) {
        super(card);
    }

    @Override
    public SacredMesa copy() {
        return new SacredMesa(this);
    }
}
