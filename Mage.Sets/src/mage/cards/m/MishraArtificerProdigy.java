
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class MishraArtificerProdigy extends CardImpl {

    public MishraArtificerProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an artifact spell, you may search your graveyard, hand, and/or library for a card with the same name as that spell and put it onto the battlefield. If you search your library this way, shuffle it.
        this.addAbility(new MishraArtificerProdigyTriggeredAbility());
    }

    private MishraArtificerProdigy(final MishraArtificerProdigy card) {
        super(card);
    }

    @Override
    public MishraArtificerProdigy copy() {
        return new MishraArtificerProdigy(this);
    }
}

class MishraArtificerProdigyTriggeredAbility extends TriggeredAbilityImpl {

    MishraArtificerProdigyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MishraArtificerProdigyEffect(), true);
    }

    MishraArtificerProdigyTriggeredAbility(final MishraArtificerProdigyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MishraArtificerProdigyTriggeredAbility copy() {
        return new MishraArtificerProdigyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isArtifact(game)) {
                ((MishraArtificerProdigyEffect) this.getEffects().get(0)).setName(spell.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may search your graveyard, hand, and/or library for a card with the same name as that spell and put it onto the battlefield. If you search your library this way, shuffle.";
    }
}

class MishraArtificerProdigyEffect extends OneShotEffect {

    private String cardName;

    MishraArtificerProdigyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your graveyard, hand, and/or library for a card named <i>" + cardName + "</i> and put it onto the battlefield. If you search your library this way, shuffle.";
    }

    MishraArtificerProdigyEffect(final MishraArtificerProdigyEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
    }

    @Override
    public MishraArtificerProdigyEffect copy() {
        return new MishraArtificerProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard filter = new FilterCard("card named " + this.cardName);
            filter.add(new NamePredicate(cardName));
            Card card = null;
            // Graveyard
            if (controller.chooseUse(Outcome.Neutral, "Search your graveyard?", source, game)) {
                // You can't fail to find the card in your graveyard because it's not hidden
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(1, 1, filter);
                if (controller.choose(Outcome.PutCardInPlay, controller.getGraveyard(), target, source, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
            }
            // Hand
            if (card == null && controller.chooseUse(Outcome.Neutral, "Search your hand?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(0, 1, filter);
                if (controller.choose(Outcome.PutCardInPlay, controller.getHand(), target, source, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
            }
            // Library
            if (card == null && controller.chooseUse(Outcome.Neutral, "Search your library?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                if (controller.searchLibrary(target, source, game)) {
                    card = game.getCard(target.getFirstTarget());
                }
                controller.shuffleLibrary(source, game);
            }
            // Put on battlefield
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }

    public void setName(String cardName) {
        this.cardName = cardName;
    }
}
