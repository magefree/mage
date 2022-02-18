
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CallForBlood extends CardImpl {

    public CallForBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");
        this.subtype.add(SubType.ARCANE);

        // As an additional cost to cast Call for Blood, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        // Target creature gets -X/-X until end of turn, where X is the sacrificed creature's power.
        this.getSpellAbility().addEffect(new BoostTargetEffect(CallForBloodDynamicValue.instance, CallForBloodDynamicValue.instance, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private CallForBlood(final CallForBlood card) {
        super(card);
    }

    @Override
    public CallForBlood copy() {
        return new CallForBlood(this);
    }
}

enum CallForBloodDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Card sourceCard = game.getCard(sourceAbility.getSourceId());
        if (sourceCard != null) {
            for (Cost cost : sourceAbility.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    Permanent p = (Permanent) game.getLastKnownInformation(((SacrificeTargetCost) cost).getPermanents().get(0).getId(), Zone.BATTLEFIELD);
                    if (p != null) {
                        return -1 * p.getPower().getValue();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public CallForBloodDynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the sacrificed creature's power";
    }

    @Override
    public String toString() {
        return "-X";
    }
}
