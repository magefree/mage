package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreatureCard;
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
 * @author muz
 */
public final class RadagastOfRhosgobel extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("The first creature spell you cast each turn");

    static {
        filter.add(new RadagastFirstCastCreatureSpellPredicate());
    }

    public RadagastOfRhosgobel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // The first creature spell you cast each turn costs {2} less to cast and can be cast as though it had flash.
        SimpleStaticAbility ability = new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2));
        ability.addEffect(new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter)
            .setText("and can be cast as though it had flash"));
        this.addAbility(ability, new RadagastOfRhosgobelWatcher());
    }

    private RadagastOfRhosgobel(final RadagastOfRhosgobel card) {
        super(card);
    }

    @Override
    public RadagastOfRhosgobel copy() {
        return new RadagastOfRhosgobel(this);
    }
}

class RadagastOfRhosgobelWatcher extends Watcher {

    private final Map<UUID, Integer> playerCreatureSpells;

    RadagastOfRhosgobelWatcher() {
        super(WatcherScope.GAME);
        playerCreatureSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature(game)) {
                playerCreatureSpells.put(event.getPlayerId(), creatureSpellsCastThisTurn(event.getPlayerId()) + 1);
            }
        }
    }

    int creatureSpellsCastThisTurn(UUID playerId) {
        return playerCreatureSpells.getOrDefault(playerId, 0);
    }

    @Override
    public void reset() {
        super.reset();
        playerCreatureSpells.clear();
    }
}

class RadagastFirstCastCreatureSpellPredicate implements ObjectSourcePlayerPredicate<Card> {

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null && input.getObject().isCreature(game)) {
            RadagastOfRhosgobelWatcher watcher = game.getState().getWatcher(RadagastOfRhosgobelWatcher.class);
            return watcher != null && watcher.creatureSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first creature spell you cast each turn";
    }
}
