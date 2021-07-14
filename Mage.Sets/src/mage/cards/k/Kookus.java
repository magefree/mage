
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author TheElk801
 */
public final class Kookus extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new NamePredicate("Keeper of Kookus"));
    }

    public Kookus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, if you don't control a creature named Keeper of Kookus, Kookus deals 3 damage to you and attacks this turn if able.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(3), TargetController.YOU, false);
        ability.addEffect(new AttacksIfAbleSourceEffect(Duration.EndOfTurn, false));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                "At the beginning of your upkeep, "
                + "if you don't control a creature named Keeper of Kookus, "
                + "{this} deals 3 damage to you and attacks this turn if able"
        ));

        // {R}: Kookus gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.R)));
    }

    private Kookus(final Kookus card) {
        super(card);
    }

    @Override
    public Kookus copy() {
        return new Kookus(this);
    }
}
