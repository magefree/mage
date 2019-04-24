
package mage.cards.c;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

/**
 * @author LevelX2
 */
public final class ConduitOfRuin extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card with converted mana cost 7 or greater");
    private static final FilterCreatureCard filterCost = new FilterCreatureCard("The first creature spell");

    static {
        filter.add(new ColorlessPredicate());
        filter.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 6));
        filterCost.add(new FirstCastCreatureSpellPredicate());
    }

    public ConduitOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When you cast Conduit of Ruin, you may search your library for a colorless creature card with converted mana cost 7 or greater, then shuffle your library and put that card on top of it.
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        this.addAbility(new CastSourceTriggeredAbility(new SearchLibraryPutOnLibraryEffect(target, true, true), true));

        // The first creature spell you cast each turn costs {2} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filterCost, 2);
        effect.setText("The first creature spell you cast each turn costs {2} less to cast.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect), new ConduitOfRuinWatcher());
    }

    public ConduitOfRuin(final ConduitOfRuin card) {
        super(card);
    }

    @Override
    public ConduitOfRuin copy() {
        return new ConduitOfRuin(this);
    }
}

class ConduitOfRuinWatcher extends Watcher {

    Map<UUID, Integer> playerCreatureSpells;
    int spellCount = 0;

    public ConduitOfRuinWatcher() {
        super(ConduitOfRuinWatcher.class.getSimpleName(), WatcherScope.GAME);
        playerCreatureSpells = new HashMap<>();
    }

    public ConduitOfRuinWatcher(final ConduitOfRuinWatcher watcher) {
        super(watcher);
        this.playerCreatureSpells = new HashMap<>();
        playerCreatureSpells.putAll(watcher.playerCreatureSpells);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature()) {
                playerCreatureSpells.put(event.getPlayerId(), creatureSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    public int creatureSpellsCastThisTurn(UUID playerId) {
        return playerCreatureSpells.getOrDefault(playerId, 0);
    }

    @Override
    public ConduitOfRuinWatcher copy() {
        return new ConduitOfRuinWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        playerCreatureSpells.clear();
    }
}

class FirstCastCreatureSpellPredicate implements ObjectPlayerPredicate<ObjectPlayer<Controllable>> {

    @Override
    public boolean apply(ObjectPlayer<Controllable> input, Game game) {
        if (input.getObject() instanceof Spell
                && ((Spell) input.getObject()).isCreature()) {
            ConduitOfRuinWatcher watcher = (ConduitOfRuinWatcher) game.getState().getWatchers().get(ConduitOfRuinWatcher.class.getSimpleName());
            return watcher != null && watcher.creatureSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first creature spell you cast each turn";
    }
}
