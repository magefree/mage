package mage.cards.n;

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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class N1Starfighter extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public N1Starfighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W/U}{W/U}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever N-1 Starfighter deals combat damage to a player, you may pay {1}{W/U}. If you do,
        // exile another target creature you control, then return that card to the battlefield under its owner's control.
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{1}{W/U}"));
        doIfCostPaid.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false).concatBy(","));

        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(doIfCostPaid, false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private N1Starfighter(final N1Starfighter card) {
        super(card);
    }

    @Override
    public N1Starfighter copy() {
        return new N1Starfighter(this);
    }
}
