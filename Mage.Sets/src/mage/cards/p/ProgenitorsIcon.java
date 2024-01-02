package mage.cards.p;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author jimga150
 */
public final class ProgenitorsIcon extends CardImpl {

    public ProgenitorsIcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // As Progenitor's Icon enters the battlefield, choose a creature type.
        // Based on Circle of Solace
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: The next spell of the chosen type you cast this turn can be cast as though it had flash.
        // Based on Ride the Avalanche
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProgenitorsIconAsThoughEffect(), new TapSourceCost());
        ability.addWatcher(new ProgenitorsIconWatcher());
        this.addAbility(ability);
    }

    private ProgenitorsIcon(final ProgenitorsIcon card) {
        super(card);
    }

    @Override
    public ProgenitorsIcon copy() {
        return new ProgenitorsIcon(this);
    }
}

class ProgenitorsIconAsThoughEffect extends AsThoughEffectImpl {

    public ProgenitorsIconAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next spell of the chosen type you cast this turn can be cast as though it had flash";
    }

    private ProgenitorsIconAsThoughEffect(final ProgenitorsIconAsThoughEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        ProgenitorsIconWatcher.addPlayer(source.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ProgenitorsIconAsThoughEffect copy() {
        return new ProgenitorsIconAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId)) {
            return false;
        }
        if (!ProgenitorsIconWatcher.checkPlayer(affectedControllerId, game)) {
            discard();
            return false;
        }
        Card card = game.getCard(sourceId);
        if (card == null) return false;

        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        return card.getSubtype().contains(subType);
    }
}

class ProgenitorsIconWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    public ProgenitorsIconWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            playerSet.remove(event.getPlayerId());
        }
    }

    public static void addPlayer(UUID playerId, Game game) {
        game.getState().getWatcher(ProgenitorsIconWatcher.class).playerSet.add(playerId);
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        return game.getState().getWatcher(ProgenitorsIconWatcher.class).playerSet.contains(playerId);
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }
}
