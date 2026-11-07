package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author DarioBarrabinoDev
 */
public final class LadyLokiAgentOfChaos extends CardImpl {

    public LadyLokiAgentOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you cast your first instant, sorcery, or Villain spell each turn, exile it, then exile cards from the top of your library until you exile a nonland card. Lady Loki deals damage to each opponent equal to the difference between that spell's mana value and that nonland card's mana value. You may cast that card without paying its mana cost.
        this.addAbility(new LadyLokiAgentOfChaosTriggeredAbility());
    }

    private LadyLokiAgentOfChaos(final LadyLokiAgentOfChaos card) {
        super(card);
    }

    @Override
    public LadyLokiAgentOfChaos copy() {
        return new LadyLokiAgentOfChaos(this);
    }
}

class LadyLokiAgentOfChaosTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterSpell filter = new FilterSpell("an instant, sorcery, or Villain spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.VILLAIN.getPredicate()
        ));
    }

    LadyLokiAgentOfChaosTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LadyLokiAgentOfChaosEffect());
        setTriggerPhrase("Whenever you cast your first instant, sorcery, or Villain spell each turn, ");
    }

    private LadyLokiAgentOfChaosTriggeredAbility(final LadyLokiAgentOfChaosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LadyLokiAgentOfChaosTriggeredAbility copy() {
        return new LadyLokiAgentOfChaosTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher
                .getSpellsCastThisTurn(this.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(s -> filter.match(s, game))
                .collect(Collectors.toList());
        if (spells.size() != 1 || !spells.get(0).getId().equals(event.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }
}

class LadyLokiAgentOfChaosEffect extends OneShotEffect {

    LadyLokiAgentOfChaosEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile it, then exile cards from the top of your library until you exile a nonland card. "
                + "{this} deals damage to each opponent equal to the difference between that spell's mana value "
                + "and that nonland card's mana value. You may cast that card without paying its mana cost";
    }

    private LadyLokiAgentOfChaosEffect(final LadyLokiAgentOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public LadyLokiAgentOfChaosEffect copy() {
        return new LadyLokiAgentOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        boolean noLongerOnStack = false; // spell was countered or exiled already by another effect
        if (spell == null) {
            spell = ((Spell) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.STACK));
            noLongerOnStack = true;
        }
        if (spell == null) {
            return false;
        }
        int manaValue = spell.getManaValue();
        if (!noLongerOnStack) {
            player.moveCards(spell, Zone.EXILED, source, game);
        }
        Card card = getCard(player, game, source);
        if (card == null) {
            return true;
        }
        int diff = Math.abs(manaValue - card.getManaValue());
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            opponent.damage(diff, source.getSourceId(), source, game);
        }
        CardUtil.castSpellWithAttributesForFree(player, source, game, card);
        return true;
    }

    private static Card getCard(Player player, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            if (!card.isLand(game)) {
                return card;
            }
        }
        return null;
    }
}
