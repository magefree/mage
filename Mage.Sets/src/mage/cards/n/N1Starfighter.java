
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class N1Starfighter extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(new AnotherPredicate());
    }

    public N1Starfighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W/U}{W/U}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever N-1 Starfighter deals combat damage to a player, you may pay {1}{W/U}. If you do, exile another creature you control, then return that card to the battlefield under its owner's control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(new ExileTargetForSourceEffect(), new ManaCostsImpl("{1}{W/U}")), false);
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, true);
        effect.setText(", then return the card to the battlefield under their owner's control");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public N1Starfighter(final N1Starfighter card) {
        super(card);
    }

    @Override
    public N1Starfighter copy() {
        return new N1Starfighter(this);
    }
}
