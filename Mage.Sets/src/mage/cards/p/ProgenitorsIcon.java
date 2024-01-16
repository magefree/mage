package mage.cards.p;

import java.util.*;

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

    ProgenitorsIconAsThoughEffect() {
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
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);

        if (!ProgenitorsIconWatcher.checkPlayerCast(affectedControllerId, subType, game)) {
            discard();
            return false;
        }
        Card card = game.getCard(sourceId);
        if (card == null){
            return false;
        }

        return card.getSubtype().contains(subType);
    }
}

class ProgenitorsIconWatcher extends Watcher {

    // Handles multiple instances of this same card choosing the same or different subtypes for the same player
    private final Map<UUID, Set<SubType>> playerSubTypesChosen = new HashMap<>();

    public ProgenitorsIconWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST){

            Spell spell = game.getSpellOrLKIStack(event.getSourceId());
            Set<SubType> chosenSubTypes = playerSubTypesChosen.get(event.getPlayerId());

            if (spell == null || chosenSubTypes == null){
                return;
            }

            // Remove all matching subtypes. If you tap two Progenitor's Icons for flash applying to Human and Goblin
            // subtypes, then cast a Human Goblin with flash, both instances of Progenitor's Icon abilities get used.
            for (SubType subType : spell.getSubtype()){
                chosenSubTypes.remove(subType);
            }
        }
    }

    public static void addPlayer(UUID playerId, SubType subtype, Game game) {
        // Add playerId to map linked to empty list if not already there
        // Will not add a subtype twice
        game.getState()
                .getWatcher(ProgenitorsIconWatcher.class)
                .playerSubTypesChosen
                .computeIfAbsent(playerId, x -> new HashSet<>())
                .add(subtype);
    }

    public static boolean checkPlayerCast(UUID playerId, SubType subtype, Game game) {
        // If a player has tapped this for its effect,
        // and at least one of those tapped Progenitor's Icons has this subtype selected
        return game.getState()
                .getWatcher(ProgenitorsIconWatcher.class)
                .playerSubTypesChosen
                .getOrDefault(playerId, Collections.emptySet())
                .contains(subtype);
    }

    @Override
    public void reset() {
        super.reset();
        playerSubTypesChosen.clear();
    }
}
