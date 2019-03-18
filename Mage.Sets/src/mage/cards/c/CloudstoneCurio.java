
package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class CloudstoneCurio extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nonartifact permanent");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public CloudstoneCurio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a nonartifact permanent enters the battlefield under your control, you may return another permanent you control that shares a card type with it to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new CloudstoneCurioEffect(), filter, true, SetTargetPointer.PERMANENT, "", true));

    }

    public CloudstoneCurio(final CloudstoneCurio card) {
        super(card);
    }

    @Override
    public CloudstoneCurio copy() {
        return new CloudstoneCurio(this);
    }
}

class CloudstoneCurioEffect extends OneShotEffect {

    public CloudstoneCurioEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "you may return another permanent you control that shares a permanent type with it to its owner's hand";
    }

    public CloudstoneCurioEffect(final CloudstoneCurioEffect effect) {
        super(effect);
    }

    @Override
    public CloudstoneCurioEffect copy() {
        return new CloudstoneCurioEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent triggeringCreature = ((FixedTarget) getTargetPointer()).getTargetedPermanentOrLKIBattlefield(game);
            if (triggeringCreature == null) {
                triggeringCreature = (Permanent) game.getLastKnownInformation(getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
            }
            if (triggeringCreature != null) {
                FilterPermanent filter = new FilterPermanent("another permanent you control that shares a permanent type with " + triggeringCreature.getName());
                filter.add(Predicates.not(new PermanentIdPredicate(triggeringCreature.getId())));
                filter.add(new ControllerPredicate(TargetController.YOU));
                Set<CardTypePredicate> cardTypes = new HashSet<>();
                for (CardType cardType : triggeringCreature.getCardType()) {
                    if (cardType.isPermanentType()) {
                        cardTypes.add(new CardTypePredicate(cardType));
                    }
                }
                filter.add(Predicates.or(cardTypes));
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);

                if (target.canChoose(controller.getId(), game) && controller.chooseTarget(outcome, target, source, game)) {
                    Permanent returningCreature = game.getPermanent(target.getFirstTarget());
                    if (returningCreature != null) {
                        controller.moveCards(returningCreature, Zone.HAND, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
