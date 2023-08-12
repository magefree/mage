package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FaerieDragonToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Elminster extends CardImpl {

    public Elminster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELMINSTER);
        this.setStartingLoyalty(5);

        // Whenever you scry, the next instant or sorcery spell you cast this turn costs {X} less to cast, where X is the number of cards looked at while scrying this way.
        this.addAbility(new ScryTriggeredAbility(new ElminsterReductionEffect()), new ElminsterWatcher());

        // +2: Draw a card, then scry 2.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 2);
        ability.addEffect(new ScryEffect(2, false).concatBy(", then"));
        this.addAbility(ability);

        // −3: Exile the top card of your library. Create number of 1/1 blue Faerie Dragon creature tokens with flying equal to that card's mana value.
        this.addAbility(new LoyaltyAbility(new ElminsterExileEffect(), -3));

        // Elminster can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private Elminster(final Elminster card) {
        super(card);
    }

    @Override
    public Elminster copy() {
        return new Elminster(this);
    }
}

class ElminsterReductionEffect extends CostModificationEffectImpl {

    private int spellsCast;

    ElminsterReductionEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = " the next instant or sorcery spell you cast this turn costs {X} less to cast, " +
                "where X is the number of cards looked at while scrying this way";
    }

    private ElminsterReductionEffect(final ElminsterReductionEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        ElminsterWatcher watcher = game.getState().getWatcher(ElminsterWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, (Integer) getValue("amount"));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        ElminsterWatcher watcher = game.getState().getWatcher(ElminsterWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }
        if (!(abilityToModify instanceof SpellAbility)
                || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return spellCard != null && spellCard.isInstantOrSorcery(game);
    }

    @Override
    public ElminsterReductionEffect copy() {
        return new ElminsterReductionEffect(this);
    }
}

class ElminsterExileEffect extends OneShotEffect {

    ElminsterExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. Create a number of 1/1 " +
                "blue Faerie Dragon creature tokens with flying equal to that card's mana value";
    }

    private ElminsterExileEffect(final ElminsterExileEffect effect) {
        super(effect);
    }

    @Override
    public ElminsterExileEffect copy() {
        return new ElminsterExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.getManaValue() > 0) {
            new FaerieDragonToken().putOntoBattlefield(card.getManaValue(), game, source);
        }
        return true;
    }
}

class ElminsterWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    ElminsterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}
