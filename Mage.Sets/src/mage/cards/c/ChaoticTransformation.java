package mage.cards.c;

import java.util.HashSet;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.*;

/**
 *
 * @author weirddan455
 */
public final class ChaoticTransformation extends CardImpl {

    public ChaoticTransformation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Exile up to one target artifact, up to one target creature, up to one target enchantment, up to one target planeswalker, and/or up to one target land.
        // For each permanent exiled this way, its controller reveals cards from the top of their library until they reveal a card that shares a card type with it, puts that card onto the battlefield, then shuffles.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetPlaneswalkerPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetLandPermanent(0, 1));
        this.getSpellAbility().addEffect(new ChaoticTransformationEffect());
    }

    private ChaoticTransformation(final ChaoticTransformation card) {
        super(card);
    }

    @Override
    public ChaoticTransformation copy() {
        return new ChaoticTransformation(this);
    }
}

class ChaoticTransformationEffect extends OneShotEffect {

    public ChaoticTransformationEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Exile up to one target artifact, up to one target creature, up to one target enchantment, up to one target planeswalker, and/or up to one target land. "
                + "For each permanent exiled this way, its controller reveals cards from the top of their library until they reveal a card that shares a card type with it, puts that card onto the battlefield, then shuffles.";
    }

    private ChaoticTransformationEffect(final ChaoticTransformationEffect effect) {
        super(effect);
    }

    @Override
    public ChaoticTransformationEffect copy() {
        return new ChaoticTransformationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourceController = game.getPlayer(source.getControllerId());
        if (sourceController == null) {
            return false;
        }
        for (Target target : source.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            Player permanentController = game.getPlayer(permanent.getControllerId());
            HashSet<CardType> types = new HashSet<>(permanent.getCardType(game));
            if (!sourceController.moveCards(permanent, Zone.EXILED, source, game) || permanentController == null) {
                continue;
            }
            Cards revealedCards = new CardsImpl();
            Card toBattlefield = null;
            for (Card card : permanentController.getLibrary().getCards(game)) {
                revealedCards.add(card);
                if (sharesType(card, types, game)) {
                    toBattlefield = card;
                    break;
                }
            }
            if (!revealedCards.isEmpty()) {
                permanentController.revealCards(source, revealedCards, game);
            }
            if (toBattlefield != null) {
                permanentController.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            }
            permanentController.shuffleLibrary(source, game);
        }
        return true;
    }

    private boolean sharesType(Card card, HashSet<CardType> types, Game game) {
        for (CardType type : card.getCardType(game)) {
            if (types.contains(type)) {
                return true;
            }
        }
        return false;
    }
}
