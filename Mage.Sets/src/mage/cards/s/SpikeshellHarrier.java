package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpikeshellHarrier extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SpikeshellHarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When this creature enters, return target creature or Vehicle an opponent controls to its owner's hand. If that opponent's speed is greater than each other player's speed, reduce that opponent's speed by 1. This effect can't reduce their speed below 1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SpikeshellHarrierEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpikeshellHarrier(final SpikeshellHarrier card) {
        super(card);
    }

    @Override
    public SpikeshellHarrier copy() {
        return new SpikeshellHarrier(this);
    }
}

class SpikeshellHarrierEffect extends OneShotEffect {

    SpikeshellHarrierEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature or Vehicle an opponent controls to its owner's hand. " +
                "If that opponent's speed is greater than each other player's speed, " +
                "reduce that opponent's speed by 1. This effect can't reduce their speed below 1";
    }

    private SpikeshellHarrierEffect(final SpikeshellHarrierEffect effect) {
        super(effect);
    }

    @Override
    public SpikeshellHarrierEffect copy() {
        return new SpikeshellHarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        int minSpeed = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .filter(uuid -> !uuid.equals(player.getId()))
                .map(game::getPlayer)
                .mapToInt(Player::getSpeed)
                .min()
                .orElse(0);
        if (player.getSpeed() > minSpeed) {
            player.decreaseSpeed(game);
        }
        return true;
    }
}
