package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterHistoricSpell;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlistairTheBrigadier extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();
    private static final FilterPermanent filter2 = new FilterControlledPermanent("historic permanents you control");

    static {
        filter.add(HistoricPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2, null);
    private static final Hint hint = new ValueHint("Historic permanents you control", xValue);

    public AlistairTheBrigadier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a historic spell, create a 1/1 white Soldier creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SoldierToken()), filter, false
        ));

        // Whenever Alistair attacks, you may pay {8}. If you do, creatures you control get +X/+X until end of turn, where X is the number of historic permanents you control.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn), new GenericManaCost(8)
        )).addHint(hint));
    }

    private AlistairTheBrigadier(final AlistairTheBrigadier card) {
        super(card);
    }

    @Override
    public AlistairTheBrigadier copy() {
        return new AlistairTheBrigadier(this);
    }
}
