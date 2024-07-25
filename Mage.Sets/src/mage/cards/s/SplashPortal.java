package mage.cards.s;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author jimga150
 */
public final class SplashPortal extends CardImpl {

    public SplashPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");
        

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        // If that creature is a Bird, Frog, Otter, or Rat, draw a card.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new SplashPortalEffect());
    }

    private SplashPortal(final SplashPortal card) {
        super(card);
    }

    @Override
    public SplashPortal copy() {
        return new SplashPortal(this);
    }
}

// Based on EssenceFluxEffect
class SplashPortalEffect extends OneShotEffect {

    private static final List<SubType> checkedSubTypes = Arrays.asList(
            SubType.BIRD, SubType.FROG, SubType.OTTER, SubType.RAT
    );

    SplashPortalEffect() {
        super(Outcome.Benefit);
        staticText = ", then return that card to the battlefield under its owner's control. " +
                "If that creature is a Bird, Frog, Otter, or Rat, draw a card.";
    }

    private SplashPortalEffect(final SplashPortalEffect effect) {
        super(effect);
    }

    @Override
    public SplashPortalEffect copy() {
        return new SplashPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cardsToBattlefield = new CardsImpl();

        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            UUID mainCardId = CardUtil.getMainCardId(game, targetId);
            if (game.getExile().containsId(mainCardId, game)) {
                cardsToBattlefield.add(mainCardId);
            } else {
                Card card = game.getCard(targetId);
                if (card instanceof MeldCard) {
                    MeldCard meldCard = (MeldCard) card;
                    Card topCard = meldCard.getTopHalfCard();
                    Card bottomCard = meldCard.getBottomHalfCard();
                    if (topCard.getZoneChangeCounter(game) == meldCard.getTopLastZoneChangeCounter() && game.getExile().containsId(topCard.getId(), game)) {
                        cardsToBattlefield.add(topCard);
                    }
                    if (bottomCard.getZoneChangeCounter(game) == meldCard.getBottomLastZoneChangeCounter() && game.getExile().containsId(bottomCard.getId(), game)) {
                        cardsToBattlefield.add(bottomCard);
                    }
                }
            }
        }

        if (cardsToBattlefield.isEmpty()){
            return true;
        }
        controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);

        for (UUID cardId : cardsToBattlefield) {
            Permanent permanent = game.getPermanent(cardId);
            if (permanent == null){
                continue;
            }
            boolean hasSubType = checkedSubTypes.stream()
                    .anyMatch(subType -> permanent.hasSubtype(subType, game));
            if (hasSubType) {
                Effect effect = new DrawCardSourceControllerEffect(1);
                return effect.apply(game, source);
            }
        }
        return true;
    }
}