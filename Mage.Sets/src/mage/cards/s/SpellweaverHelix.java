package mage.cards.s;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.*;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author emerald000
 */
public final class SpellweaverHelix extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery cards");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public SpellweaverHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - When Spellweaver Helix enters the battlefield, you may exile two target sorcery cards from a single graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), true);
        ability.addTarget(new TargetCardInASingleGraveyard(2, 2, filter));
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // Whenever a player casts a card, if it has the same name as one of the cards exiled with Spellweaver Helix, you may copy the other. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new SpellweaverHelixTriggeredAbility());
    }

    private SpellweaverHelix(final SpellweaverHelix card) {
        super(card);
    }

    @Override
    public SpellweaverHelix copy() {
        return new SpellweaverHelix(this);
    }
}

class SpellweaverHelixTriggeredAbility extends TriggeredAbilityImpl {

    SpellweaverHelixTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpellweaverHelixCastEffect(), false);
    }

    private SpellweaverHelixTriggeredAbility(final SpellweaverHelixTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellweaverHelixTriggeredAbility copy() {
        return new SpellweaverHelixTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || spell.getCard() == null || spell.getCard().isCopy()) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = this
                .getEffects()
                .stream()
                .map(effect -> (Spell) effect.getValue("spellCast"))
                .findFirst()
                .orElse(null);
        return spell != null
                && Optional.ofNullable(game)
                .map(Game::getExile)
                .map(exile -> exile.getExileZone(CardUtil.getExileZoneId(game, this)))
                .filter(Objects::nonNull)
                .map(e -> e.getCards(game))
                .map(Collection::stream)
                .map(s -> s.anyMatch(card -> card.sharesName(spell, game)))
                .orElse(false);
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a card, if it has the same name as one of the cards exiled with {this}, " +
                "you may copy the other. If you do, you may cast the copy without paying its mana cost.";
    }
}

class SpellweaverHelixCastEffect extends OneShotEffect {

    SpellweaverHelixCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may copy the other. If you do, you may cast the copy without paying its mana cost";
    }

    private SpellweaverHelixCastEffect(final SpellweaverHelixCastEffect effect) {
        super(effect);
    }

    @Override
    public SpellweaverHelixCastEffect copy() {
        return new SpellweaverHelixCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (player == null || spell == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone);
        Set<UUID> matching = exileZone
                .getCards(game)
                .stream()
                .filter(card -> card.sharesName(spell, game))
                .map(MageItem::getId)
                .collect(Collectors.toSet());
        if (matching.size() < 2) { // If two or more cards match then all cards are valid choices
            cards.removeAll(matching);
        }
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD);
                player.choose(outcome, cards, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        return card != null
                && new ExileTargetCardCopyAndCastEffect(true, true)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
    }
}
