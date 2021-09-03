package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SigardaChampionOfLight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Humans");
    private static final FilterCard filter2 = new FilterCreatureCard("Human creature card");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public SigardaChampionOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Humans you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));

        // Coven — Whenever Sigarda attacks, if you control three or more creatures with different powers, look at the top five cards of your library. You may reveal a Human creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                        StaticValue.get(5), false, StaticValue.get(1), filter2,
                        Zone.LIBRARY, false, true, false, Zone.HAND, true
                ).setBackInRandomOrder(true)), CovenCondition.instance, AbilityWord.COVEN.formatWord() +
                "Whenever {this} attacks, if you control three or more creatures with different powers, " +
                "look at the top five cards of your library. You may reveal a Human creature card from among them " +
                "and put it into your hand. Put the rest on the bottom of your library in a random order."
        ).addHint(CovenHint.instance));
    }

    private SigardaChampionOfLight(final SigardaChampionOfLight card) {
        super(card);
    }

    @Override
    public SigardaChampionOfLight copy() {
        return new SigardaChampionOfLight(this);
    }
}
