package mage.cards.i;

import java.util.UUID;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author weirddan455
 */
public final class InSearchOfGreatness extends CardImpl {

    public InSearchOfGreatness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // At the beginning of your upkeep, you may cast a permanent spell from your hand with converted mana cost
        // equal to 1 plus the highest converted mana cost among other permanents you control
        // without paying its mana cost. If you don't, scry 1.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new InSearchOfGreatnessEffect(),
                TargetController.YOU, false, false
        ));
    }

    private InSearchOfGreatness(final InSearchOfGreatness card) {
        super(card);
    }

    @Override
    public InSearchOfGreatness copy() {
        return new InSearchOfGreatness(this);
    }
}

class InSearchOfGreatnessEffect extends OneShotEffect {

    public InSearchOfGreatnessEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast a permanent spell from your hand with mana value "
                + "equal to 1 plus the highest mana value among other permanents you control "
                + "without paying its mana cost. If you don't, scry 1";
    }

    private InSearchOfGreatnessEffect(final InSearchOfGreatnessEffect effect) {
        super(effect);
    }

    @Override
    public InSearchOfGreatnessEffect copy() {
        return new InSearchOfGreatnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int cmc = 0;
        UUID permId = source.getSourceId();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controller.getId())) {
            if (permanent != null && !permanent.getId().equals(permId)) {
                int permCmc = permanent.getManaValue();
                if (permCmc > cmc) {
                    cmc = permCmc;
                }
            }
        }
        if (controller.chooseUse(outcome, "Cast a permanent spell from your hand with CMC equal to "
                + ++cmc + "?", source, game)) {
            FilterPermanentCard filter = new FilterPermanentCard("permanent spell from your hand");
            filter.add(Predicates.not(CardType.LAND.getPredicate()));
            filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
            TargetCardInHand target = new TargetCardInHand(filter);
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                    boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                    if (cardWasCast) {
                        return true;
                    }
                }
            }
        }
        return controller.scry(1, source, game);
    }
}
