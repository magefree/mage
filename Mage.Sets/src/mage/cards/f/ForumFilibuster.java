package mage.cards.f;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.AuraCardCanAttachToPermanentId;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InklingToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class ForumFilibuster extends CardImpl {

    public ForumFilibuster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // At the beginning of your upkeep, create a 2/1 white and black Inkling creature token with flying. When you do, return up to one target Aura or Equipment card from your graveyard to the battlefield attached to that token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ForumFilibusterEffect()));
    }

    private ForumFilibuster(final ForumFilibuster card) {
        super(card);
    }

    @Override
    public ForumFilibuster copy() {
        return new ForumFilibuster(this);
    }
}

class ForumFilibusterEffect extends OneShotEffect {

    ForumFilibusterEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 2/1 white and black Inkling creature token with flying. " +
            "When you do, return up to one target Aura or Equipment card from your graveyard " +
            "to the battlefield attached to that token";
    }

    private ForumFilibusterEffect(final ForumFilibusterEffect effect) {
        super(effect);
    }

    @Override
    public ForumFilibusterEffect copy() {
        return new ForumFilibusterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect tokenEffect = new CreateTokenEffect(new InklingToken());
        if (!tokenEffect.apply(game, source)) {
            return false;
        }

        List<UUID> tokenIds = tokenEffect.getLastAddedTokenIds();
        if (tokenIds.isEmpty()) {
            return true;
        }

        UUID tokenId = tokenIds.get(0);
        FilterCard filter = new FilterCard("Aura or Equipment card from your graveyard");
        filter.add(Predicates.or(
            Predicates.and(
                SubType.AURA.getPredicate(),
                new AuraCardCanAttachToPermanentId(tokenId)
            ),
            SubType.EQUIPMENT.getPredicate()
        ));

        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ForumFilibusterAttachEffect(tokenId), false,
            "when you do, return up to one target Aura or Equipment card from your graveyard to the battlefield attached to that token"
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(0, 1, filter));
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}

class ForumFilibusterAttachEffect extends OneShotEffect {

    private final UUID tokenId;

    ForumFilibusterAttachEffect(UUID tokenId) {
        super(Outcome.PutCardInPlay);
        staticText = "return up to one target Aura or Equipment card from your graveyard to the battlefield attached to that token";
        this.tokenId = tokenId;
    }

    private ForumFilibusterAttachEffect(final ForumFilibusterAttachEffect effect) {
        super(effect);
        this.tokenId = effect.tokenId;
    }

    @Override
    public ForumFilibusterAttachEffect copy() {
        return new ForumFilibusterAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Permanent tokenPermanent = game.getPermanent(tokenId);
        if (controller == null || card == null || tokenPermanent == null) {
            return false;
        }
        if (!tokenPermanent.cantBeAttachedBy(card, source, game, true)) {
            game.getState().setValue("attachTo:" + card.getId(), tokenPermanent);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            tokenPermanent.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}
