package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author TheElk801
 */
public final class WicksPatrol extends CardImpl {

    public WicksPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When Wick's Patrol enters, mill three cards. When you do, target creature an opponent controls gets -X/-X until end of turn, where X is the greatest mana value among cards in your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WicksPatrolEffect()));
    }

    private WicksPatrol(final WicksPatrol card) {
        super(card);
    }

    @Override
    public WicksPatrol copy() {
        return new WicksPatrol(this);
    }
}

class WicksPatrolEffect extends OneShotEffect {

    WicksPatrolEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. When you do, target creature an opponent controls " +
                "gets -X/-X until end of turn, where X is the greatest mana value among cards in your graveyard";
    }

    private WicksPatrolEffect(final WicksPatrolEffect effect) {
        super(effect);
    }

    @Override
    public WicksPatrolEffect copy() {
        return new WicksPatrolEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        // From the release notes: If your library has two or fewer cards in it when Wick's Patrol's ability resolves,
        // you'll mill as many cards as you can, but the reflexive ability won't trigger.
        if (player == null || player.millCards(3, source, game).size() < 3) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(WicksPatrolValue.instance, WicksPatrolValue.instance), false
        ).setTriggerPhrase("When you do, ");
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

enum WicksPatrolValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return -1 * Optional
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
    public WicksPatrolValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest mana value among cards in your graveyard";
    }

    @Override
    public String toString() {
        return "-X";
    }

    @Override
    public int getSign() {
        return -1;
    }
}
