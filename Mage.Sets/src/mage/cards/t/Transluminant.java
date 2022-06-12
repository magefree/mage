
package mage.cards.t;

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
import mage.game.permanent.token.SpiritWhiteToken;

/**
 *
 * @author LoneFox
 */
public final class Transluminant extends CardImpl {

    public Transluminant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, Sacrifice Transluminant: Create a 1/1 white Spirit creature token with flying at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(
            new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken()))),
            new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private Transluminant(final Transluminant card) {
        super(card);
    }

    @Override
    public Transluminant copy() {
        return new Transluminant(this);
    }
}
