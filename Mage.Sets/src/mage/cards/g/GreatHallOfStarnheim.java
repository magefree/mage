package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.AngelWarriorVigilanceToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GreatHallOfStarnheim extends CardImpl {

    public GreatHallOfStarnheim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Great Hall of Starnheim enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {W}{W}{B}, {T}, Sacrifice Great Hall of Starnheim and a creature you control:
        // Create a 4/4 white Angel Warrior creature token with flying and vigilance.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new AngelWarriorVigilanceToken()),
                new ManaCostsImpl<>("{W}{W}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
        cost.setText("and a creature you control");
        ability.addCost(cost);
        this.addAbility(ability);
    }

    private GreatHallOfStarnheim(final GreatHallOfStarnheim card) {
        super(card);
    }

    @Override
    public GreatHallOfStarnheim copy() {
        return new GreatHallOfStarnheim(this);
    }
}
