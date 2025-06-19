package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class RashmiEternitiesCrafter extends CardImpl {

    public RashmiEternitiesCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cast your first spell each turn, reveal the top card of your library. 
        // If it's a nonland card with converted mana cost less than that spell's, you may cast it 
        // without paying its mana cost. If you don't cast the revealed card, put it into your hand.
        this.addAbility(new RashmiEternitiesCrafterTriggeredAbility());
    }

    private RashmiEternitiesCrafter(final RashmiEternitiesCrafter card) {
        super(card);
    }

    @Override
    public RashmiEternitiesCrafter copy() {
        return new RashmiEternitiesCrafter(this);
    }
}

class RashmiEternitiesCrafterTriggeredAbility extends SpellCastControllerTriggeredAbility {

    RashmiEternitiesCrafterTriggeredAbility() {
        super(new RashmiEternitiesCrafterEffect(), false);
        setTriggerPhrase("Whenever you cast your first spell each turn, ");
    }

    private RashmiEternitiesCrafterTriggeredAbility(final RashmiEternitiesCrafterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RashmiEternitiesCrafterTriggeredAbility copy() {
        return new RashmiEternitiesCrafterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game)
                && game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getCount(event.getPlayerId()) == 1;
    }
}

class RashmiEternitiesCrafterEffect extends OneShotEffect {

    RashmiEternitiesCrafterEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal the top card of your library. You may cast it without paying its mana cost " +
                "if it's a spell with lesser mana value. If you don't cast it, put it into your hand.";
    }

    private RashmiEternitiesCrafterEffect(final RashmiEternitiesCrafterEffect effect) {
        super(effect);
    }

    @Override
    public RashmiEternitiesCrafterEffect copy() {
        return new RashmiEternitiesCrafterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.revealCards("Rashmi, Eternities Crafter", new CardsImpl(card), game);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, spell.getManaValue()));
        CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter);
        if (Zone.LIBRARY.match(game.getState().getZone(card.getId()))) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}
