
package mage.cards.b;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DealsDamageToOneOrMoreCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PermanentsSacrificedWatcher;

/**
 * @author LevelX2
 */
public final class BriarbridgePatrol extends CardImpl {

    public BriarbridgePatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.HUMAN, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate (Create a colorless Clue artifact token with "2, Sacrifice this artifact: Draw a card.").
        this.addAbility(new DealsDamageToOneOrMoreCreaturesTriggeredAbility(new InvestigateEffect(), false, false, false));
        // At the beginning of each end step, if you sacrificed three or more Clues this turn, you may put a creature card from your hand onto the battlefield.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A), TargetController.ANY,
                BriarbridgePatrolCondition.instance, true), new PermanentsSacrificedWatcher());

    }

    private BriarbridgePatrol(final BriarbridgePatrol card) {
        super(card);
    }

    @Override
    public BriarbridgePatrol copy() {
        return new BriarbridgePatrol(this);
    }
}

enum BriarbridgePatrolCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsSacrificedWatcher watcher = game.getState().getWatcher(PermanentsSacrificedWatcher.class);
        if (watcher != null) {
            List<Permanent> sacrificedPermanents = watcher.getThisTurnSacrificedPermanents(source.getControllerId());
            if (sacrificedPermanents != null && !sacrificedPermanents.isEmpty()) {
                int amountOfClues = 0;
                for (Permanent permanent : sacrificedPermanents) {
                    if (permanent.hasSubtype(SubType.CLUE, game)) {
                        amountOfClues++;
                    }
                }
                return amountOfClues > 2;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if you sacrificed three or more Clues this turn";
    }

}
