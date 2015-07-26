package mage.abilities.effects.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

/**
 * @author klayhamn
 */
public class RippleEffect extends OneShotEffect {

    protected int rippleNumber;
    protected boolean isTargetSelf; // is the source of the ripple also the target of the ripple?

    public RippleEffect(int rippleNumber) {
        this(rippleNumber, true); // by default, the source is also the target
    }

    public RippleEffect(int rippleNumber, boolean isTargetSelf) {
        super(Outcome.PlayForFree);
        this.rippleNumber = rippleNumber;
        this.isTargetSelf = isTargetSelf;
        this.setText();
    }

    public RippleEffect(final RippleEffect effect) {
        super(effect);
        this.rippleNumber = effect.rippleNumber;
        this.isTargetSelf = effect.isTargetSelf;
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
            if (!player.chooseUse(Outcome.Neutral, "Reveal " + rippleNumber + " cards from the top of your library?", source, game)){
                return true; //fizzle
            }
            Cards cards = new CardsImpl();
            cards.addAll(player.getLibrary().getTopCards(game, rippleNumber)); // pull top cards
            player.revealCards(sourceObject.getIdName(), cards, game); // reveal the cards

            // Find out which card should be rippled
            // FIXME: I'm not sure the "isTargetSelf" flag is the most elegant solution
            String cardNameToRipple;
            if (isTargetSelf) { // if the ripple applies to the same card that triggered it
                cardNameToRipple = sourceObject.getName();
            } else { // if the ripple is caused by something else (e.g. Thrumming Stone)
                Spell spellOnStack = game.getStack().getSpell(targetPointer.getFirst(game, source));
                if (spellOnStack == null) { // if the ripple target got countered or exiled
                    spellOnStack = (Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
                }
                if (spellOnStack == null) {
                    return true; // should not happen?
                }
                cardNameToRipple = spellOnStack.getName();
            }

            FilterCard sameNameFilter = new FilterCard("card(s) with the name: \"" + cardNameToRipple + "\" to cast without paying their mana cost");
            sameNameFilter.add(new NamePredicate(cardNameToRipple));
            TargetCard target1 = new TargetCard(Zone.LIBRARY, sameNameFilter);
            target1.setRequired(false);

            // Choose cards to play for free
            while (player.isInGame() && cards.count(sameNameFilter, game) > 0 && player.choose(Outcome.PlayForFree, cards, target1, game)) {
                Card card = cards.get(target1.getFirstTarget(), game);
                if (card != null) {
                    player.cast(card.getSpellAbility(), game, true);
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

    private void setText() {
        StringBuilder sb = new StringBuilder("Ripple ").append(rippleNumber);
            sb.append(". <i>(You may reveal the top ");
            sb.append(CardUtil.numberToText(rippleNumber));
            sb.append(" cards of your library. You may cast any revealed cards with the same name as this spell without paying their mana costs. Put the rest on the bottom of your library.)</i>");
        staticText = sb.toString();
    }
}
