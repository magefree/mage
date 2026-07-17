package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SpeedYoungAvenger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with haste");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with haste");

    static {
        filter.add(new AbilityPredicate(HasteAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public SpeedYoungAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you cast a noncreature spell, you may pay {1}. When you do, target creature with haste can't be blocked this turn except by creatures with haste.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(new CantBeBlockedTargetEffect(
                filter2, Duration.EndOfTurn
        ).setText("target creature with haste can't be blocked this turn except by creatures with haste"), false);
        reflexive.addTarget(new TargetPermanent(filter));
        Ability ability = new SpellCastControllerTriggeredAbility(
            new DoWhenCostPaid(reflexive, new GenericManaCost(1), "Pay {1}?"),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        this.addAbility(ability);
    }

    private SpeedYoungAvenger(final SpeedYoungAvenger card) {
        super(card);
    }

    @Override
    public SpeedYoungAvenger copy() {
        return new SpeedYoungAvenger(this);
    }
}
