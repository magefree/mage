package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class OrmosArchiveKeeper extends CardImpl {

    public OrmosArchiveKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would draw a card while your library has no cards in it, instead put five +1/+1 counters on Ormos, Archive Keeper.
        this.addAbility(new SimpleStaticAbility(new OrmosArchiveKeeperEffect()));

        // {1}{U}{U}, Discard three cards with different names: Draw five cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(5), new ManaCostsImpl("{1}{U}{U}")
        );
        ability.addCost(new DiscardTargetCost(new OrmosArchiveKeeperTarget()));
        this.addAbility(ability);
    }

    private OrmosArchiveKeeper(final OrmosArchiveKeeper card) {
        super(card);
    }

    @Override
    public OrmosArchiveKeeper copy() {
        return new OrmosArchiveKeeper(this);
    }
}

class OrmosArchiveKeeperEffect extends ReplacementEffectImpl {

    OrmosArchiveKeeperEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card while your library has no cards in it, "
                + "instead put five +1/+1 counters on {this}";
    }

    private OrmosArchiveKeeperEffect(final OrmosArchiveKeeperEffect effect) {
        super(effect);
    }

    @Override
    public OrmosArchiveKeeperEffect copy() {
        return new OrmosArchiveKeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(5), source.getControllerId(), source, game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && !player.hasLost() && !player.getLibrary().hasCards()) {
                return true;
            }
        }
        return false;
    }
}

class OrmosArchiveKeeperTarget extends TargetCardInHand {

    private static final FilterCard filter = new FilterCard("three cards with different names");

    OrmosArchiveKeeperTarget() {
        super(3, filter);
    }

    private OrmosArchiveKeeperTarget(final OrmosArchiveKeeperTarget target) {
        super(target);
    }

    @Override
    public OrmosArchiveKeeperTarget copy() {
        return new OrmosArchiveKeeperTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && names.contains(card.getName());
        });
        return possibleTargets;
    }
}
