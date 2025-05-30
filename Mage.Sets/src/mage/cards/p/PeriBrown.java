package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PeriBrown extends CardImpl {

    private static final String rule = "The first historic spell you cast each turn has convoke. " +
            "<i>(Your creatures can help cast it. Each creature you tap while casting it pays for {1} " +
            "or one mana of that creature's color.)</i>";
    private static final FilterNonlandCard filter = new FilterNonlandCard("the first historic spell you cast each turn");
    static {
        filter.add(PeriBrownFirstHistoricCastSpellPredicate.instance);
    }

    public PeriBrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // The first historic spell you cast each turn has convoke.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter).setText(rule)),
                new PeriBrownHistoricSpellWatcher()
        );

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());

    }

    private PeriBrown(final PeriBrown card) {
        super(card);
    }

    @Override
    public PeriBrown copy() {
        return new PeriBrown(this);
    }
}

class PeriBrownHistoricSpellWatcher extends Watcher {

    // Based on Conduit of Ruin

    private final Map<UUID, Integer> historicSpells; // player id -> number

    public PeriBrownHistoricSpellWatcher() {
        super(WatcherScope.GAME);
        historicSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isHistoric(game)) {
                historicSpells.put(event.getPlayerId(), historicSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    public int historicSpellsCastThisTurn(UUID playerId) {
        return historicSpells.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        historicSpells.clear();
    }
}

enum PeriBrownFirstHistoricCastSpellPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null && input.getObject().isHistoric(game)) {
            PeriBrownHistoricSpellWatcher watcher = game.getState().getWatcher(PeriBrownHistoricSpellWatcher.class);
            return watcher != null && watcher.historicSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first historic spell you cast each turn";
    }
}
