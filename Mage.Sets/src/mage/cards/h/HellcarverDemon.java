
package mage.cards.h;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class HellcarverDemon extends CardImpl {

    public HellcarverDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellcarverDemonEffect(), false));
    }

    public HellcarverDemon(final HellcarverDemon card) {
        super(card);
    }

    @Override
    public HellcarverDemon copy() {
        return new HellcarverDemon(this);
    }
}

class HellcarverDemonEffect extends OneShotEffect {

    public HellcarverDemonEffect() {
        super(Outcome.PlayForFree);
        staticText = "sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.";
    }

    public HellcarverDemonEffect(final HellcarverDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
                if (!Objects.equals(permanent, sourceObject)) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            if (!controller.getHand().isEmpty()) {
                int cardsInHand = controller.getHand().size();
                controller.discard(cardsInHand, false, source, game);
            }
            // move cards from library to exile
            Set<Card> currentExiledCards = new HashSet<>();
            currentExiledCards.addAll(controller.getLibrary().getTopCards(game, 6));
            controller.moveCardsToExile(currentExiledCards, source, game, true, source.getSourceId(), sourceObject.getIdName());

            // cast the possible cards without paying the mana
            Cards cardsToCast = new CardsImpl();
            cardsToCast.addAll(currentExiledCards);
            boolean alreadyCast = false;
            while (!cardsToCast.isEmpty()
                    && controller.canRespond()) {
                if (!controller.chooseUse(outcome, "Cast a" + (alreadyCast ? "nother" : "") + " card exiled with " + sourceObject.getLogName() + " without paying its mana cost?", source, game)) {
                    break;
                }
                TargetCard targetCard = new TargetCard(1, Zone.EXILED, new FilterCard("nonland card to cast for free"));
                if (controller.choose(Outcome.PlayForFree, cardsToCast, targetCard, game)) {
                    alreadyCast = true;
                    Card card = game.getCard(targetCard.getFirstTarget());
                    if (card != null) {
                        if (controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game))) {
                            cardsToCast.remove(card);
                        } else {
                            game.informPlayer(controller, "You're not able to cast " + card.getIdName() + " or you canceled the casting.");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HellcarverDemonEffect copy() {
        return new HellcarverDemonEffect(this);
    }
}
