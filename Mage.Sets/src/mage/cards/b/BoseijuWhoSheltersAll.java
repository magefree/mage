
package mage.cards.b;

import java.util.*;

import mage.MageObject;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.mana.SimpleManaAbility;
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
 * @author LevelX2
 */
public final class BoseijuWhoSheltersAll extends CardImpl {

    public BoseijuWhoSheltersAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // Boseiju, Who Shelters All enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}, Pay 2 life: Add {C}. If that mana is spent on an instant or sorcery spell, that spell can't be countered.
        Mana mana = Mana.ColorlessMana(1);
        mana.setFlag(true); // used to indicate this mana ability
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, mana, new TapSourceCost());
        ability.addCost(new PayLifeCost(2));
        ability.getEffects().get(0).setText("Add {C}. If that mana is spent on an instant or sorcery spell, that spell can't be countered");
        this.addAbility(ability, new BoseijuWhoSheltersAllWatcher(ability.getOriginalId()));

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new BoseijuWhoSheltersAllCantCounterEffect()));
    }

    private BoseijuWhoSheltersAll(final BoseijuWhoSheltersAll card) {
        super(card);
    }

    @Override
    public BoseijuWhoSheltersAll copy() {
        return new BoseijuWhoSheltersAll(this);
    }
}

class BoseijuWhoSheltersAllWatcher extends Watcher {

    private final Set<MageObjectReference> spells = new HashSet<>();
    private final UUID originalId;

    public BoseijuWhoSheltersAllWatcher(UUID originalId) {
        super(WatcherScope.CARD);
        this.originalId = originalId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAID) {
            if (event.getData() != null && event.getData().equals(originalId.toString()) && event.getTargetId() != null) {
                Card spell = game.getSpell(event.getTargetId());
                if (spell != null && spell.isInstantOrSorcery(game)) {
                    spells.add(new MageObjectReference(game.getObject(event.getTargetId()), game));
                }
            }
        }
    }

    public boolean spellCantBeCountered(MageObjectReference mor) {
        return spells.contains(mor);
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }
}

class BoseijuWhoSheltersAllCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    public BoseijuWhoSheltersAllCantCounterEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
        staticText = null;
    }

    public BoseijuWhoSheltersAllCantCounterEffect(final BoseijuWhoSheltersAllCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public BoseijuWhoSheltersAllCantCounterEffect copy() {
        return new BoseijuWhoSheltersAllCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered because mana from " + sourceObject.getName() + " was spent to cast it.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        BoseijuWhoSheltersAllWatcher watcher = game.getState().getWatcher(BoseijuWhoSheltersAllWatcher.class, source.getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && watcher != null && watcher.spellCantBeCountered(new MageObjectReference(spell, game));
    }
}