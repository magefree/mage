
package mage.cards.n;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class NightmareIncursion extends CardImpl {

    public NightmareIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");


        // Search target player's library for up to X cards, where X is the number of Swamps you control, and exile them. Then that player shuffles their library.
        Effect effect = new NightmareIncursionEffect();
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(effect);

    }

    public NightmareIncursion(final NightmareIncursion card) {
        super(card);
    }

    @Override
    public NightmareIncursion copy() {
        return new NightmareIncursion(this);
    }
}

class NightmareIncursionEffect extends OneShotEffect {

    private static final  FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    boolean exiled = false;

    public NightmareIncursionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search target player's library for up to X cards, where X is the number of Swamps you control, and exile them. Then that player shuffles their library";
    }

    public NightmareIncursionEffect(final NightmareIncursionEffect effect) {
        super(effect);
    }

    @Override
    public NightmareIncursionEffect copy() {
        return new NightmareIncursionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            int amount = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard());
            if (controller.searchLibrary(target, source, game, targetPlayer.getId())) {
                List<UUID> targetId = target.getTargets();
                for (UUID targetCard : targetId) {
                    Card card = targetPlayer.getLibrary().remove(targetCard, game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, null, source.getSourceId(), game, Zone.LIBRARY, true);
                        result = true;
                    }
                }
            }
        }
        if (targetPlayer != null) {
            targetPlayer.shuffleLibrary(source, game);
        }
        return result;
    }
}
