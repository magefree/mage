package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class SwindlersScheme extends CardImpl {

    public SwindlersScheme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent casts a spell from their hand, you may reveal the top card of your library.
        // If it shares a card type with that spell, counter that spell and that opponent may cast the revealed card without paying its mana cost.
        this.addAbility(new SwindlersSchemeOpponentCastTriggeredAbility());
    }

    private SwindlersScheme(final SwindlersScheme card) {
        super(card);
    }

    @Override
    public SwindlersScheme copy() {
        return new SwindlersScheme(this);
    }
}

/**
 * TODO: Creating a custom ability since SpellCastOpponentTriggeredAbility is getting out of hand and needs
 *       to be refactored to not use telescoping constructors.
 */
class SwindlersSchemeOpponentCastTriggeredAbility extends TriggeredAbilityImpl {

    SwindlersSchemeOpponentCastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SwindlersSchemeEffect(), true);
        setTriggerPhrase("Whenever an opponent casts a spell from their hand, ");
    }

    private SwindlersSchemeOpponentCastTriggeredAbility(final SwindlersSchemeOpponentCastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
            return false;
        }

        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || spell.getFromZone() != Zone.HAND) {
            return false;
        }
        getEffects().setValue("spellCast", spell);
        return true;
    }

    @Override
    public SwindlersSchemeOpponentCastTriggeredAbility copy() {
        return new SwindlersSchemeOpponentCastTriggeredAbility(this);
    }
}

class SwindlersSchemeEffect extends OneShotEffect {

    SwindlersSchemeEffect() {
        super(Outcome.Detriment);
        this.staticText = "reveal the top card of your library. " +
                "If it shares a card type with that spell, counter that spell and that opponent may cast the revealed card without paying its mana cost.";
    }

    private SwindlersSchemeEffect(final SwindlersSchemeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        Library library = controller.getLibrary();

        Card cardFromTop = library.getFromTop(game);
        if (cardFromTop == null) {
            return false;
        }

        if (cardFromTop.getCardType(game).stream().noneMatch(spell.getCardType(game)::contains)) {
            return false;
        }
        Player opponent = game.getPlayer(spell.getControllerId());

        Effect counterEffect = new CounterTargetEffect();
        counterEffect.setTargetPointer(new FixedTarget(spell.getId()));
        counterEffect.apply(game, source);

        CardUtil.castSpellWithAttributesForFree(opponent, source, game, cardFromTop);

        return true;
    }

    @Override
    public SwindlersSchemeEffect copy() {
        return new SwindlersSchemeEffect(this);
    }
}