package mage.cards.i;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffect;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author miesma
 */
public final class InsideInformation extends CardImpl {

    public InsideInformation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Exile the top X cards of target opponent’s library.
        // You may play those cards this turn.
        // If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.
        this.getSpellAbility().addEffect(new InsideInformationExileEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().setIdentifier(MageIdentifier.InsideInformationAlternateCast);
    }

    private InsideInformation(final InsideInformation card) {
        super(card);
    }

    @Override
    public InsideInformation copy() {
        return new InsideInformation(this);
    }
}

class InsideInformationExileEffect extends OneShotEffect {

    InsideInformationExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top X cards of target opponent's library. " +
                "You may play those cards this turn. " +
                "If you cast a spell this way, pay life equal to its mana value rather than pay its mana cost.";
    }

    private InsideInformationExileEffect(final InsideInformationExileEffect effect) {
        super(effect);
    }

    @Override
    public InsideInformationExileEffect copy() {
        return new InsideInformationExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, xValue));
        if (cards.isEmpty()) {
            return false;
        }
        UUID zoneID = CardUtil.getCardExileZoneId(game, source);
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                zoneID,
                CardUtil.createObjectRelatedWindowTitle(source, game, "(may be played using life)"));
        AsThoughEffect costEffect = new InsideInformationCastEffect(zoneID);
        game.addEffect(costEffect, source);
        return true;
    }
}

class InsideInformationCastEffect extends AsThoughEffectImpl {

    private UUID zoneID;

    InsideInformationCastEffect(UUID zoneID) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.AIDontUseIt);
        this.zoneID = zoneID;
    }

    private InsideInformationCastEffect(final InsideInformationCastEffect effect) {
        super(effect);
        zoneID = effect.zoneID;
    }

    @Override
    public boolean apply(Game game, Ability source) {return true;}

    @Override
    public InsideInformationCastEffect copy() {
        return new InsideInformationCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game){

        Player controller = game.getPlayer(affectedControllerId);
        if (controller == null) {
            return false;
        }
        Card cardObject = game.getCard(objectId);
        if (cardObject == null) {
            return false;
        }
        UUID mainId = cardObject.getMainCard().getId(); // for split cards
        ExileZone exileZone = game.getExile().getExileZone(zoneID);
        if (exileZone == null || !exileZone.contains(mainId)) {
            return false;
        }
        if (!cardObject.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(cardObject.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(cardObject.getSpellAbility().getCosts());
            controller.setCastSourceIdWithAlternateMana(cardObject.getId(), null, newCosts, MageIdentifier.InsideInformationAlternateCast);
        }
        return true;
    }
}



