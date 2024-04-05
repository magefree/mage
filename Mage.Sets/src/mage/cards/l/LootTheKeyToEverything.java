package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LootTheKeyToEverything extends CardImpl {

    public LootTheKeyToEverything(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of your upkeep, exile the top X cards of your library, where X is the number of card types among other nonland permanents you control. You may play those cards this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(LootTheKeyToEverythingValue.instance, Duration.EndOfTurn)
                        .setText("exile the top X cards of your library, where X is the number of card types " +
                                "among other nonland permanents you control. You may play those cards this turn"),
                TargetController.YOU, false
        ));
    }

    private LootTheKeyToEverything(final LootTheKeyToEverything card) {
        super(card);
    }

    @Override
    public LootTheKeyToEverything copy() {
        return new LootTheKeyToEverything(this);
    }
}

enum LootTheKeyToEverythingValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                .stream()
                .map(permanent -> permanent.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public LootTheKeyToEverythingValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }
}
