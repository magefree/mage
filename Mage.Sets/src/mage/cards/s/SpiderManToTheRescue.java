package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SpiderManToTheRescue extends CardImpl {

    static final FilterControlledCreaturePermanent filter
        = new FilterControlledCreaturePermanent("nonattacking creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(AttackingPredicate.instance));
    }

    public SpiderManToTheRescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // No One Dies! -- When Spider-Man enters, you may tap him. When you do, another target nonattacking creature you control gains indestructible until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new GainAbilityTargetEffect(
            IndestructibleAbility.getInstance()
        ).setText("another target nonattacking creature you control gains indestructible until end of turn"), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            ability,
            new TapSourceCost().setText("tap him"),
            "Tap {this}?"
        )).withFlavorWord("No One Dies!"));
    }

    private SpiderManToTheRescue(final SpiderManToTheRescue card) {
        super(card);
    }

    @Override
    public SpiderManToTheRescue copy() {
        return new SpiderManToTheRescue(this);
    }
}
