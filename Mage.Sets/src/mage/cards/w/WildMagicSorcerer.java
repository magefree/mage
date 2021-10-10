package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.players.Player;

/**
 * @author TheElk801
 */
public final class WildMagicSorcerer extends CardImpl {

    public WildMagicSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // The first spell you cast from exile each turn has cascade.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect()),
                new WildMagicSorcererWatcher());
    }

    private WildMagicSorcerer(final WildMagicSorcerer card) {
        super(card);
    }

    @Override
    public WildMagicSorcerer copy() {
        return new WildMagicSorcerer(this);
    }
}

class WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    public WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "The first spell you cast from exile each turn has cascade";
    }

    public WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect(final WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect copy() {
        return new WildMagicSorcererGainCascadeFirstSpellCastFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                // only spells cast, so no copies of spells
                if ((stackObject instanceof Spell)
                        && !stackObject.isCopy()
                        && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    WildMagicSorcererWatcher watcher = game.getState().getWatcher(WildMagicSorcererWatcher.class);
                    if (watcher != null
                            && FirstSpellCastFromExileEachTurnCondition.instance.apply(game, source)) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

enum FirstSpellCastFromExileEachTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStack().isEmpty()) {
            return false;
        }
        WildMagicSorcererWatcher watcher = game.getState().getWatcher(WildMagicSorcererWatcher.class);
        StackObject so = game.getStack().getFirst();
        return so != null
                && watcher != null
                && WildMagicSorcererWatcher.checkSpell(so, game);
    }
}

class WildMagicSorcererWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    WildMagicSorcererWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL
                || event.getZone() != Zone.EXILED) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell == null) {
            return;
        }
        playerMap.computeIfAbsent(event.getPlayerId(), x -> new MageObjectReference(spell.getMainCard(), game));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkSpell(StackObject stackObject, Game game) {
        if (stackObject.isCopy()
                || !(stackObject instanceof Spell)) {
            return false;
        }
        WildMagicSorcererWatcher watcher = game.getState().getWatcher(WildMagicSorcererWatcher.class);
        return watcher.playerMap.containsKey(stackObject.getControllerId())
                && watcher.playerMap.get(stackObject.getControllerId()).refersTo(((Spell) stackObject).getMainCard(), game);
    }
}
