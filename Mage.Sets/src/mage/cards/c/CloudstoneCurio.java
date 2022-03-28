package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class CloudstoneCurio extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a nonartifact permanent");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CloudstoneCurio(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a nonartifact permanent enters the battlefield under your control, you may return another permanent you control that shares a card type with it to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new CloudstoneCurioEffect(), filter, true, SetTargetPointer.PERMANENT, "", true));

    }

    private CloudstoneCurio(final CloudstoneCurio card) {
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
            Permanent triggeringCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (triggeringCreature != null) {
                FilterPermanent filter = new FilterPermanent("another permanent you control that shares a permanent type with " + triggeringCreature.getName());
                filter.add(Predicates.not(new PermanentIdPredicate(triggeringCreature.getId())));
                filter.add(TargetController.YOU.getControllerPredicate());
                filter.add(Predicates.or(
                        triggeringCreature
                                .getCardType(game)
                                .stream()
                                .filter(CardType::isPermanentType)
                                .map(CardType::getPredicate)
                                .collect(Collectors.toSet())
                ));
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);

                if (target.canChoose(controller.getId(), source, game) && controller.chooseTarget(outcome, target, source, game)) {
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
