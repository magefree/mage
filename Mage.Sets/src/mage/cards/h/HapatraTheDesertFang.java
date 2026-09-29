package mage.cards.h;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HapatraTheDesertFang extends CardImpl {

    public HapatraTheDesertFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Hapatra enters, for each opponent, put X -1/-1 counters on up to one target creature that player controls, where X is the greatest mana value among cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(
                CounterType.M1M1.createInstance(), HapatraTheDesertFangValue.instance, Outcome.UnboostCreature
        ).setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, put X -1/-1 counters on up to one target creature that player controls, "
                        + "where X is the greatest mana value among cards in your graveyard"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        this.addAbility(ability);
    }

    private HapatraTheDesertFang(final HapatraTheDesertFang card) {
        super(card);
    }

    @Override
    public HapatraTheDesertFang copy() {
        return new HapatraTheDesertFang(this);
    }
}

enum HapatraTheDesertFangValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 1 * Optional
                .ofNullable(sourceAbility)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(game))
                .map(Collection::stream)
                .map(stream -> stream.mapToInt(MageObject::getManaValue))
                .map(IntStream::max)
                .map(i -> i.orElse(0))
                .orElse(0);
    }

    @Override
    public HapatraTheDesertFangValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among cards in your graveyard";
    }

    @Override
    public String toString() {
        return "X";
    }
}
