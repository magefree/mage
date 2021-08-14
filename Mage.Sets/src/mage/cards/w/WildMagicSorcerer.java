package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        this.addAbility(new SimpleStaticAbility(new WildMagicSorcererEffect()), new WildMagicSorcererWatcher());
    }

    private WildMagicSorcerer(final WildMagicSorcerer card) {
        super(card);
    }

    @Override
    public WildMagicSorcerer copy() {
        return new WildMagicSorcerer(this);
    }
}

class WildMagicSorcererEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    public WildMagicSorcererEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "the first spell you cast from exile each turn has cascade";
    }

    public WildMagicSorcererEffect(final WildMagicSorcererEffect effect) {
        super(effect);
    }

    @Override
    public WildMagicSorcererEffect copy() {
        return new WildMagicSorcererEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (StackObject stackObject : game.getStack()) {
            // only spells cast, so no copies of spells
            if (stackObject.isControlledBy(source.getControllerId())
                    && WildMagicSorcererWatcher.checkSpell(stackObject, game)) {
                game.getState().addOtherAbility(((Spell) stackObject).getCard(), cascadeAbility);
            }
        }
        return true;
    }
}

class WildMagicSorcererWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    WildMagicSorcererWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getZone() == Zone.EXILED) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
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
        if (stackObject.isCopy() || !(stackObject instanceof Spell)) {
            return false;
        }
        WildMagicSorcererWatcher watcher = game.getState().getWatcher(WildMagicSorcererWatcher.class);
        return watcher.playerMap.containsKey(stackObject.getControllerId())
                && watcher.playerMap.get(stackObject).refersTo(((Spell) stackObject).getMainCard(), game);
    }
}
