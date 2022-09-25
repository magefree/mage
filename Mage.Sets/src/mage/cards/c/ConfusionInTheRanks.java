
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class ConfusionInTheRanks extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public ConfusionInTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever an artifact, creature, or enchantment enters the battlefield, its controller chooses target permanent another player controls that shares a card type with it. Exchange control of those permanents.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(
                        Duration.EndOfGame,
                        "its controller chooses target permanent "
                                + "another player controls that shares a card type with it. "
                                + "Exchange control of those permanents"
                ),
                filter, false, SetTargetPointer.PERMANENT, null
        );
        ability.addTarget(new TargetPermanent());
        ability.setTargetAdjuster(ConfusionInTheRanksAdjuster.instance);
        this.addAbility(ability);
    }

    private ConfusionInTheRanks(final ConfusionInTheRanks card) {
        super(card);
    }

    @Override
    public ConfusionInTheRanks copy() {
        return new ConfusionInTheRanks(this);
    }
}

enum ConfusionInTheRanksAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID enteringPermanentId = null;
        for (Effect effect : ability.getEffects()) {
            enteringPermanentId = effect.getTargetPointer().getFirst(game, ability);
        }
        if (enteringPermanentId == null) {
            return;
        }
        Permanent enteringPermanent = game.getPermanent(enteringPermanentId);
        if (enteringPermanent == null) {
            return;
        }
        ability.getTargets().clear();
        SharesCardTypePredicate predicate = new SharesCardTypePredicate(enteringPermanent.getCardType(game));
        FilterPermanent filterTarget = new FilterPermanent(predicate.toString() + " you don't control");
        filterTarget.add(predicate);
        filterTarget.add(Predicates.not(new ControllerIdPredicate(enteringPermanent.getControllerId())));
        TargetPermanent target = new TargetPermanent(filterTarget);
        target.setTargetController(enteringPermanent.getControllerId());
        ability.getTargets().add(target);
    }
}
