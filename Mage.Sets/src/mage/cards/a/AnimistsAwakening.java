
package mage.cards.a;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AnimistsAwakening extends CardImpl {

    public AnimistsAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{G}");

        // Reveal the top X cards of your library. Put all land cards from among them onto the battlefield tapped and the rest on the bottom of your library in any order.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, untap those lands.
        this.getSpellAbility().addEffect(new AnimistsAwakeningEffect());
    }

    private AnimistsAwakening(final AnimistsAwakening card) {
        super(card);
    }

    @Override
    public AnimistsAwakening copy() {
        return new AnimistsAwakening(this);
    }
}

class AnimistsAwakeningEffect extends OneShotEffect {

    public AnimistsAwakeningEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top X cards of your library. Put all land cards from among them onto the battlefield tapped and the rest on the bottom of your library in any order."
                + "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, untap those lands";
    }

    public AnimistsAwakeningEffect(final AnimistsAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        int xValue = source.getManaCostsToPay().getX();
        cards.addAll(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (Card card : cards.getCards(new FilterLandCard(), source.getControllerId(), source, game)) {
                cards.remove(card);
                toBattlefield.add(card);
            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, true, false, true, null);
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);

            if (SpellMasteryCondition.instance.apply(game, source)) {
                for (Card card : toBattlefield) {
                    Permanent land = game.getPermanent(card.getId());
                    if (land != null) {
                        land.untap(game);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public AnimistsAwakeningEffect copy() {
        return new AnimistsAwakeningEffect(this);
    }

}
