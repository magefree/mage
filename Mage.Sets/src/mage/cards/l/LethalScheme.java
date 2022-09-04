package mage.cards.l;

import java.util.*;
import java.util.stream.Collectors;

import javafx.util.Pair;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.Watcher;

/**
 *
 * @author Susucre
 */
public final class LethalScheme extends CardImpl {

    public LethalScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        // Each creature that convoked Lethal Scheme connives.
        this.getSpellAbility().addWatcher(new LethalSchemeWatcher());
        this.getSpellAbility().addEffect(new LethalSchemeEffect());
    }

    private LethalScheme(final LethalScheme card) {
        super(card);
    }

    @Override
    public LethalScheme copy() {
        return new LethalScheme(this);
    }
}

// Based loosely on "Venerated Loxodon" and "Change of Plans"
class LethalSchemeEffect extends OneShotEffect {

    public LethalSchemeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each creature that convoked Lethal Scheme connives.";
    }

    public LethalSchemeEffect(final LethalSchemeEffect effect) {
        super(effect);
    }

    @Override
    public LethalSchemeEffect copy() {
        return new LethalSchemeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        LethalSchemeWatcher watcher = game.getState().getWatcher(LethalSchemeWatcher.class);
        if (watcher != null) {
            MageObjectReference mor = new MageObjectReference(source.getSourceId(), game);
            Set<MageObjectReference> creatures = watcher.getConvokingCreatures(mor);
            if (creatures != null) {
                Set<Pair<UUID,Permanent>> playerPermanentsPairs =
                    creatures
                        .stream()
                        .map(creatureMOR -> creatureMOR.getPermanentOrLKIBattlefield(game))
                        .filter(Objects::nonNull)
                        .map(permanent -> new Pair<>(permanent.getControllerId(),permanent))
                        .collect(Collectors.toSet());

                Map<Player, Set<Permanent>> permanentsPerPlayer = new HashMap<>();

                playerPermanentsPairs.forEach(pair -> {
                    Player player = game.getPlayer(pair.getKey());
                    if(!permanentsPerPlayer.containsKey(player)){
                        permanentsPerPlayer.put(player, new HashSet<>());
                    }
                    permanentsPerPlayer.get(player).add(pair.getValue());
                });

                if (playerPermanentsPairs.isEmpty()) {
                    return false;
                }

                for (Player player : game
                    .getState()
                    .getPlayersInRange(source.getControllerId(), game)
                    .stream()
                    .map(game::getPlayer)
                    .filter(Objects::nonNull)
                    .filter(player -> permanentsPerPlayer.containsKey(player))
                    .collect(Collectors.toList())) {

                    Set<Permanent> permanents = permanentsPerPlayer.get(player);

                    while (permanents.size() > 0) {
                        Choice choiceForThisLoop = new ChoiceImpl(true);
                        choiceForThisLoop.setMessage("Choose next connive to resolve.");

                        permanents.stream()
                                .forEach(permanent -> choiceForThisLoop.getChoices().add(permanent.getIdName()));

                        player.choose(Outcome.Neutral, choiceForThisLoop, game);

                        String choice = choiceForThisLoop.getChoice();
                        Permanent choicePermanent = permanents.stream().filter(permanent -> permanent.getIdName().equals(choice)).findFirst().get();

                        ConniveSourceEffect.connive(choicePermanent, 1, source, game);
                        permanents.remove(choicePermanent);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

// Based on "Venerated Loxodon"
class LethalSchemeWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> convokingCreatures = new HashMap<>();

    public LethalSchemeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CONVOKED) {
            Spell spell = game.getSpell(event.getSourceId());
            Permanent tappedCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (spell != null && tappedCreature != null) {
                MageObjectReference convokedSpell = new MageObjectReference(spell.getSourceId(), game);
                Set<MageObjectReference> creatures;
                if (convokingCreatures.containsKey(convokedSpell)) {
                    creatures = convokingCreatures.get(convokedSpell);
                } else {
                    creatures = new HashSet<>();
                    convokingCreatures.put(convokedSpell, creatures);
                }
                creatures.add(new MageObjectReference(tappedCreature, game));
            }
        }
    }

    public Set<MageObjectReference> getConvokingCreatures(MageObjectReference mor) {
        return convokingCreatures.get(mor);
    }

    @Override
    public void reset() {
        super.reset();
        convokingCreatures.clear();
    }

}
