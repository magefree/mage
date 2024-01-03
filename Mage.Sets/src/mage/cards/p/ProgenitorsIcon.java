package mage.cards.p;

import java.util.*;

import javafx.util.Pair;
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
import mage.game.stack.Spell;
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
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        ProgenitorsIconWatcher.addPlayer(source.getControllerId(), subType, game);
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
        if (card == null){
            return false;
        }

        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        return card.getSubtype().contains(subType);
    }
}

class ProgenitorsIconWatcher extends Watcher {

    // Handles multiple instances of this same card choosing the same or different subtypes for the same player
    private final ArrayList<Pair<UUID, SubType>> playerSubTypesChosen = new ArrayList<>();

    public ProgenitorsIconWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST){
            UUID playerID = event.getPlayerId();
            Spell spell = game.getSpellOrLKIStack(event.getSourceId());
            for (SubType subType : spell.getSubtype()){
                Pair<UUID, SubType> pair = new Pair<>(playerID, subType);
                if (playerSubTypesChosen.remove(pair)){
                    break;
                }
            }
        }
    }

    public static void addPlayer(UUID playerId, SubType subtype, Game game) {
        game.getState().getWatcher(ProgenitorsIconWatcher.class).playerSubTypesChosen.add(
                new Pair<>(playerId, subtype)
        );
    }

    public static boolean checkPlayer(UUID playerId, Game game) {
        for (Pair<UUID, SubType> pair : game.getState().getWatcher(ProgenitorsIconWatcher.class).playerSubTypesChosen){
            if (pair.getKey() == playerId){
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        playerSubTypesChosen.clear();
    }
}
