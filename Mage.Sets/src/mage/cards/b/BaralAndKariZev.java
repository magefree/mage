package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FirstMateRagavanToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class BaralAndKariZev extends CardImpl {

    public BaralAndKariZev(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you cast your first instant or sorcery spell each turn, you may cast a spell with lesser mana value that shares a card type with it from your hand without paying its mana cost. If you don't, create First Mate Ragavan, a legendary 2/1 red Monkey Pirate creature token. It gains haste until end of turn.
        this.addAbility(new BaralAndKariZevTriggeredAbility());
    }

    private BaralAndKariZev(final BaralAndKariZev card) {
        super(card);
    }

    @Override
    public BaralAndKariZev copy() {
        return new BaralAndKariZev(this);
    }
}

class BaralAndKariZevTriggeredAbility extends TriggeredAbilityImpl {

    BaralAndKariZevTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BaralAndKariZevEffect());
        this.addWatcher(new SpellsCastWatcher());
    }

    private BaralAndKariZevTriggeredAbility(final BaralAndKariZevTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BaralAndKariZevTriggeredAbility copy() {
        return new BaralAndKariZevTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        List<Spell> spells = game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(getControllerId())
                .stream()
                .filter(s -> s.isInstantOrSorcery(game))
                .collect(Collectors.toList());
        if (spells.size() != 1 || !spells.get(0).getId().equals(spell.getId())) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first instant or sorcery spell each turn, " +
                "you may cast a spell with lesser mana value that shares a card type with it from your hand " +
                "without paying its mana cost. If you don't, create First Mate Ragavan, " +
                "a legendary 2/1 red Monkey Pirate creature token. It gains haste until end of turn.";
    }
}

class BaralAndKariZevEffect extends OneShotEffect {

    BaralAndKariZevEffect() {
        super(Outcome.Benefit);
    }

    private BaralAndKariZevEffect(final BaralAndKariZevEffect effect) {
        super(effect);
    }

    @Override
    public BaralAndKariZevEffect copy() {
        return new BaralAndKariZevEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        FilterCard filter = new FilterCard();
        filter.add(Predicates.or(spell
                .getCardType(game)
                .stream()
                .map(CardType::getPredicate)
                .collect(Collectors.toList())));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, spell.getManaValue()));
        if (CardUtil.castSpellWithAttributesForFree(player, source, game, player.getHand(), filter)) {
            return true;
        }
        Token token = new FirstMateRagavanToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
