package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Kookus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("you don't control a creature named Keeper of Kookus");

    static {
        filter.add(new NamePredicate("Keeper of Kookus"));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public Kookus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, if you don't control a creature named Keeper of Kookus, Kookus deals 3 damage to you and attacks this turn if able.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(3)).withInterveningIf(condition);
        ability.addEffect(new AttacksIfAbleSourceEffect(
                Duration.EndOfTurn, false
        ).setText("and attacks this turn if able"));
        this.addAbility(ability);

        // {R}: Kookus gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")
        ));
    }

    private Kookus(final Kookus card) {
        super(card);
    }

    @Override
    public Kookus copy() {
        return new Kookus(this);
    }
}
