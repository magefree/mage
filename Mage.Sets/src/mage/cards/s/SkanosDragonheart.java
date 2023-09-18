package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class SkanosDragonheart extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Greatest power among Dragon cards in your graveyard or other Dragons you control",
            SkanosDragonheartValue.instance
    );

    public SkanosDragonheart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Skanos Dragonheart attacks, it gets +X/+X until end of turn, where X is the greatest power among Dragon cards in your graveyard or other Dragons you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                SkanosDragonheartValue.instance, SkanosDragonheartValue.instance,
                Duration.EndOfTurn, true, "it"
        )).addHint(hint));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private SkanosDragonheart(final SkanosDragonheart card) {
        super(card);
    }

    @Override
    public SkanosDragonheart copy() {
        return new SkanosDragonheart(this);
    }
}

enum SkanosDragonheartValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.DRAGON);
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(SubType.DRAGON.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Stream.concat(
                game.getBattlefield()
                        .getActivePermanents(filter, sourceAbility.getControllerId(), sourceAbility, game)
                        .stream(),
                game.getPlayer(sourceAbility.getControllerId())
                        .getGraveyard()
                        .getCards(filter2, game)
                        .stream()
        )
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public SkanosDragonheartValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest power among Dragon cards in your graveyard or other Dragons you control";
    }

    @Override
    public String toString() {
        return "X";
    }
}
