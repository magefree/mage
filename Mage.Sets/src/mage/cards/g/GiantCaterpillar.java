
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ButterflyToken;

/**
 *
 * @author LoneFox
 */
public final class GiantCaterpillar extends CardImpl {

    public GiantCaterpillar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {G}, Sacrifice Giant Caterpillar: Create a 1/1 green Insect creature token with flying named Butterfly at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new CreateTokenEffect(new ButterflyToken()))),
                new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GiantCaterpillar(final GiantCaterpillar card) {
        super(card);
    }

    @Override
    public GiantCaterpillar copy() {
        return new GiantCaterpillar(this);
    }
}
