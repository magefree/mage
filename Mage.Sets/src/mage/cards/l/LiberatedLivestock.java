package mage.cards.l;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.*;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author Codermann63, xenohedron
 */
public final class LiberatedLivestock extends CardImpl {

    public LiberatedLivestock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.OX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Liberated Livestock dies, create a 1/1 white Cat creature token with lifelink, a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token.
        // For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.
        this.addAbility(new DiesSourceTriggeredAbility(new LiberatedLivestockEffect()));
    }

    private LiberatedLivestock(final LiberatedLivestock card) {
        super(card);
    }

    @Override
    public LiberatedLivestock copy() {
        return new LiberatedLivestock(this);
    }
}

class LiberatedLivestockEffect extends OneShotEffect {

    LiberatedLivestockEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 1/1 white Cat creature token with lifelink, a 1/1 white Bird creature token with flying, and a 2/4 white Ox creature token. For each of those tokens, you may put an Aura card from your hand and/or graveyard onto the battlefield attached to it.";
    }

    private LiberatedLivestockEffect(final LiberatedLivestockEffect effect) {super(effect);}

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Token> tokens = Arrays.asList(new CatToken2(), new BirdToken(), new OxToken());
        tokens.forEach(token -> token.putOntoBattlefield(1, game, source, source.getControllerId()));
        game.getState().processAction(game);

        for (Token token : tokens) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent == null) {
                    continue;
                }
                FilterCard filter = new FilterCard("Aura from your hand or graveyard that can attach to " + tokenPermanent.getName());
                filter.add(SubType.AURA.getPredicate());
                filter.add(new AuraCardCanAttachToPermanentId(tokenPermanent.getId()));
                Cards auraCards = new CardsImpl();
                auraCards.addAllCards(controller.getHand().getCards(filter, game));
                auraCards.addAllCards(controller.getGraveyard().getCards(filter, game));
                TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
                target.withNotTarget(true);
                controller.chooseTarget(outcome, auraCards, target, source, game);
                Card auraCard = game.getCard(target.getFirstTarget());
                if (auraCard != null && !tokenPermanent.cantBeAttachedBy(auraCard, source, game, true)) {
                    game.getState().setValue("attachTo:" + auraCard.getId(), tokenPermanent);
                    controller.moveCards(auraCard, Zone.BATTLEFIELD, source, game);
                    tokenPermanent.addAttachment(auraCard.getId(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public LiberatedLivestockEffect copy() {
        return new LiberatedLivestockEffect(this);
    }
}
