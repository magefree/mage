package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 * @author klayhamn
 */
public class RippleAbility extends TriggeredAbilityImpl {

    protected final int rippleNumber;

    public RippleAbility(int rippleNumber) {
        super(Zone.STACK, new RippleEffect(rippleNumber), false);
        this.rippleNumber = rippleNumber;
    }

    public RippleAbility(RippleAbility ability) {
        super(ability);
        this.rippleNumber = ability.rippleNumber;

    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getSourceId().equals(this.getSourceId());

    }

    @Override
    public RippleAbility copy() {
        return new RippleAbility(this);
    }

    @Override
    public String getRule() {
        return "ripple " + rippleNumber + " <i>(When you cast this spell, you may reveal the top " + CardUtil.numberToText(rippleNumber) + " cards of your library. You may cast any revealed cards with the same name as this spell without paying their mana costs. Put the rest on the bottom of your library.)</i>";
    }

}

class RippleEffect extends OneShotEffect {

    protected int rippleNumber;

    public RippleEffect(int rippleNumber) {
        super(Outcome.PlayForFree);
        this.rippleNumber = rippleNumber;
    }

    public RippleEffect(final RippleEffect effect) {
        super(effect);
        this.rippleNumber = effect.rippleNumber;
    }

    @Override
    public RippleEffect copy() {
        return new RippleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null) {
            if (!player.chooseUse(Outcome.Neutral, "Reveal " + rippleNumber + " cards from the top of your library?", source, game)) {
                return true; //fizzle
            }
            // reveal top cards from library
            Cards cards = new CardsImpl();
            cards.addAll(player.getLibrary().getTopCards(game, rippleNumber));
            player.revealCards(sourceObject.getIdName(), cards, game);

            // determine which card should be rippled
            String cardNameToRipple = sourceObject.getName();
            FilterCard sameNameFilter = new FilterCard("card(s) with the name: \"" + cardNameToRipple + "\" to cast without paying their mana cost");
            sameNameFilter.add(new NamePredicate(cardNameToRipple));
            TargetCard target1 = new TargetCard(Zone.LIBRARY, sameNameFilter);
            target1.setRequired(false);

            // choose cards to play for free
            while (player.canRespond() && cards.count(sameNameFilter, game) > 0 && player.choose(Outcome.PlayForFree, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    player.cast(player.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);

                    cards.remove(card);
                }
                target1.clearChosen();
            }
            // move cards that weren't cast to the bottom of the library
            player.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }

        return false;
    }

}
