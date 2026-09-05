package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.DemonstrateAbility;
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
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class EldaConjurerofSpectacle extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("The first nonlegendary creature spell you cast each turn");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filter.add(new EldaFirstCastCreatureSpellPredicate());
    }

    public EldaConjurerofSpectacle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //The first nonlegendary creature spell you cast each turn has demonstrate
        SimpleStaticAbility ability = new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new DemonstrateAbility(), filter)
                        .setText("When you cast that spell, you may copy it. If you do, choose an opponent to also copy it. Each copy becomes a token."));
        this.addAbility(ability, new EldaConjurerofSpectacleWatcher());
    }

    private EldaConjurerofSpectacle(final EldaConjurerofSpectacle card) {
        super(card);
    }

    @Override
    public EldaConjurerofSpectacle copy() {
        return new EldaConjurerofSpectacle(this);
    }
}

class EldaConjurerofSpectacleWatcher extends Watcher {

    private final Map<UUID, Integer> playerCreatureSpells;

    EldaConjurerofSpectacleWatcher() {
        super(WatcherScope.GAME);
        playerCreatureSpells = new HashMap<>();
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null && spell.isCreature(game) && !spell.isLegendary(game)) {
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

class EldaFirstCastCreatureSpellPredicate implements ObjectSourcePlayerPredicate<Card> {

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (input.getObject() != null && input.getObject().isCreature(game) && !input.getObject().isLegendary(game)) {
            EldaConjurerofSpectacleWatcher watcher = game.getState().getWatcher(EldaConjurerofSpectacleWatcher.class);
            return watcher != null && watcher.creatureSpellsCastThisTurn(input.getPlayerId()) == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "The first nonlegendary creature spell you cast each turn";
    }
}