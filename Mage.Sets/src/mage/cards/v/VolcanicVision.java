
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class VolcanicVision extends CardImpl {

    public VolcanicVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");

        // Return target instant or sorcery card from your graveyard to your hand. Volcanic Visions deals damage equal to that card's converted mana cost to each creature your opponents control. Exile Volcanic Vision.
        this.getSpellAbility().addEffect(new VolcanicVisionReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard")));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private VolcanicVision(final VolcanicVision card) {
        super(card);
    }

    @Override
    public VolcanicVision copy() {
        return new VolcanicVision(this);
    }
}

class VolcanicVisionReturnToHandTargetEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VolcanicVisionReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = "Return target instant or sorcery card from your graveyard to your hand. {this} deals damage equal to that card's mana value to each creature your opponents control";
    }

    public VolcanicVisionReturnToHandTargetEffect(final VolcanicVisionReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public VolcanicVisionReturnToHandTargetEffect copy() {
        return new VolcanicVisionReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            switch (game.getState().getZone(targetId)) {
                case GRAVEYARD:
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        int damage = card.getManaValue();
                        if (damage > 0) {
                            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                                creature.damage(damage, source.getSourceId(), source, game, false, true);
                            }
                        }
                    }
                    break;
            }
        }
        return true;
    }

}
