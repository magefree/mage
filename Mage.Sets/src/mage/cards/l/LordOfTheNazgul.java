package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.RingBearerPredicate;
import mage.game.permanent.token.WraithToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class LordOfTheNazgul extends CardImpl {

    private static final FilterControlledCreaturePermanent filterWraith = new FilterControlledCreaturePermanent("Wraiths you control");
    private static final FilterPermanent filterRingBearer = new FilterPermanent("Ring-bearers");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filterWraith, ComparisonType.MORE_THAN, 8);
    private static final Hint hint = new ValueHint(
            "Number of Wraiths you control", new PermanentsOnBattlefieldCount(filterWraith)
    );

    static {
        filterWraith.add(SubType.WRAITH.getPredicate());
        filterRingBearer.add(RingBearerPredicate.instance);
    }

    public LordOfTheNazgul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRAITH, SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wraiths you control have protection from Ring-bearers.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new ProtectionAbility(filterRingBearer), Duration.WhileOnBattlefield, filterWraith
        )));

        // Whenever you cast an instant or sorcery spell, create a 3/3 black Wraith creature token with menace. Then if
        // you control nine or more Wraiths, Wraiths you control have base power and toughness 9/9 until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new WraithToken()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new SetBasePowerToughnessAllEffect(9, 9, Duration.EndOfTurn, filterWraith, true),
                condition, "Then if you control nine or more Wraiths, Wraiths you control have base power and toughness 9/9 until end of turn"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private LordOfTheNazgul(final LordOfTheNazgul card) {
        super(card);
    }

    @Override
    public LordOfTheNazgul copy() {
        return new LordOfTheNazgul(this);
    }
}
