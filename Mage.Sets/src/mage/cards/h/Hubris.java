
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Hubris extends CardImpl {

    public Hubris(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature and all Auras attached to it to their owners' hand.
        this.getSpellAbility().addEffect(new HubrisReturnEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private Hubris(final Hubris card) {
        super(card);
    }

    @Override
    public Hubris copy() {
        return new Hubris(this);
    }
}

class HubrisReturnEffect extends OneShotEffect {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public HubrisReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target creature and all Auras attached to it to their owners' hands";
    }

    private HubrisReturnEffect(final HubrisReturnEffect effect) {
        super(effect);
    }

    @Override
    public HubrisReturnEffect copy() {
        return new HubrisReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID targetId : targetPointer.getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    Cards cardsToHand = new CardsImpl();
                    for (UUID cardId : creature.getAttachments()) {
                        Permanent card = game.getPermanent(cardId);
                        if (card != null && card.hasSubtype(SubType.AURA, game)) {
                            cardsToHand.add(card);
                        }
                    }
                    cardsToHand.add(creature);
                    controller.moveCards(cardsToHand, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
