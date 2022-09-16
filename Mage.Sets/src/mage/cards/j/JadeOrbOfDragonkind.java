package mage.cards.j;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author NicolasCamachoP
 */
public final class JadeOrbOfDragonkind extends CardImpl {

    public JadeOrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");
        

        // {T}: Add {G}. When you spend this mana to cast a Dragon creature spell, it enters the battlefield with an additional +1/+1 counter on it and gains hexproof until your next turn.
        Mana mana = Mana.GreenMana(1);
        mana.setFlag(true);
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.getEffects().get(0).setText("Add {G}. When you spend this mana to cast a Dragon creature spell," +
                " it enters the battlefield with an additional +1/+1 counter on it and gains hexproof until your" +
                " next turn.");
        this.addAbility(ability);
        // it enters the battlefield with an additional +1/+1 counter on it
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new JadeOrbOfDragonkindAdditionalCounterEffect()));
        // gains hexproof until your next turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new JadeOrbOfDragonkindHexproofEffect()), new JadeOrbOfDragonkindWatcher());
    }

    private JadeOrbOfDragonkind(final JadeOrbOfDragonkind card) {
        super(card);
    }

    @Override
    public JadeOrbOfDragonkind copy() {
        return new JadeOrbOfDragonkind(this);
    }
}

class JadeOrbOfDragonkindWatcher extends Watcher {

    private List<UUID> creatures = new ArrayList<>();

    public JadeOrbOfDragonkindWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.MANA_PAID) {
            return;
        }

        MageObject target = game.getObject(event.getTargetId());
        if (!(target instanceof Spell)) {
            return;
        }

        // Mana from Orb
        if (!event.getFlag()) {
            return;
        }

        if (event.getSourceId() == null || !event.getSourceId().equals(this.getSourceId())) {
            return;
        }

        if (target.isCreature(game) && target.hasSubtype(SubType.DRAGON, game)) {
            this.creatures.add(((Spell) target).getCard().getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        creatures.clear();
    }

    public boolean creatureCastWithOrbsMana(UUID permanentId){
        return creatures.contains(permanentId);
    }

}

class JadeOrbOfDragonkindHexproofEffect extends ContinuousEffectImpl {

    public JadeOrbOfDragonkindHexproofEffect() {
        super(Duration.UntilYourNextTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "it and gains hexproof until your next turn";
    }

    public JadeOrbOfDragonkindHexproofEffect(final mage.cards.j.JadeOrbOfDragonkindHexproofEffect effect) {
        super(effect);
    }

    @Override
    public ContinuousEffect copy() {
        return new mage.cards.j.JadeOrbOfDragonkindHexproofEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        JadeOrbOfDragonkindWatcher watcher = game.getState().getWatcher(JadeOrbOfDragonkindWatcher.class, source.getSourceId());
        if (watcher == null) {
            return false;
        }

        for (Permanent perm : game.getBattlefield().getAllActivePermanents()) {
            if (watcher.creatureCastWithOrbsMana(perm.getId())) {
                perm.addAbility(HexproofAbility.getInstance(), source.getSourceId(), game);
            }
        }
        return true;
    }
}
class JadeOrbOfDragonkindAdditionalCounterEffect extends ReplacementEffectImpl {

    public JadeOrbOfDragonkindAdditionalCounterEffect() {
        super(Duration.OneUse, Outcome.BoostCreature, false);
        staticText = "When you spend this mana to cast a Dragon creature spell, it enters the battlefield with an additional +1/+1 counter on it";
    }

    private JadeOrbOfDragonkindAdditionalCounterEffect(JadeOrbOfDragonkindAdditionalCounterEffect effect) {
        super(effect);
    }
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        JadeOrbOfDragonkindWatcher watcher = game.getState().getWatcher(JadeOrbOfDragonkindWatcher.class, source.getSourceId());
        return watcher != null && watcher.creatureCastWithOrbsMana(event.getTargetId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public JadeOrbOfDragonkindAdditionalCounterEffect copy() {
        return new JadeOrbOfDragonkindAdditionalCounterEffect(this);
    }
}