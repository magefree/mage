package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.ClueArtifactToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DeclarationInStone extends CardImpl {

    public DeclarationInStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature and all other creatures its controller controls with the same name as that creature.
        // That player investigates for each nontoken creature exiled this way.
        this.getSpellAbility().addEffect(new DeclarationInStoneEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DeclarationInStone(final DeclarationInStone card) {
        super(card);
    }

    @Override
    public DeclarationInStone copy() {
        return new DeclarationInStone(this);
    }
}

class DeclarationInStoneEffect extends OneShotEffect {

    public DeclarationInStoneEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature and all other creatures its controller controls with the same name as that creature. That player investigates for each nontoken creature exiled this way.";
    }

    public DeclarationInStoneEffect(final DeclarationInStoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetPermanent != null) {
                Set<Card> cardsToExile = new HashSet<>();
                int nonTokenCount = 0;
                if (CardUtil.haveEmptyName(targetPermanent)) { // face down creature
                    cardsToExile.add(targetPermanent);
                    if (!(targetPermanent instanceof PermanentToken)) {
                        nonTokenCount++;
                    }
                } else {
                    if (cardsToExile.add(targetPermanent)
                            && !(targetPermanent instanceof PermanentToken)) {
                        nonTokenCount++;
                    }
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, targetPermanent.getControllerId(), game)) {
                        if (!permanent.getId().equals(targetPermanent.getId())
                                && CardUtil.haveSameNames(permanent, targetPermanent)) {
                            cardsToExile.add(permanent);
                            // exiled count only matters for non-tokens
                            if (!(permanent instanceof PermanentToken)) {
                                nonTokenCount++;
                            }
                        }
                    }
                }
                controller.moveCards(cardsToExile, Zone.EXILED, source, game);
                game.getState().processAction(game);
                if (nonTokenCount > 0) {
                    new ClueArtifactToken().putOntoBattlefield(nonTokenCount, game, source, targetPermanent.getControllerId(), false, false);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public DeclarationInStoneEffect copy() {
        return new DeclarationInStoneEffect(this);
    }
}
