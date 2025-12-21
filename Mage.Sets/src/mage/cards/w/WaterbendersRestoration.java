package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.WaterbendXCost;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterbendersRestoration extends CardImpl {

    public WaterbendersRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, waterbend {X}.
        this.getSpellAbility().addCost(new WaterbendXCost());
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("as an additional cost to cast this spell, waterbend {X}")
        ).setRuleAtTheTop(true));

        // Exile X target creatures you control. Return those cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect()
                .withTargetDescription("X target creatures you control"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private WaterbendersRestoration(final WaterbendersRestoration card) {
        super(card);
    }

    @Override
    public WaterbendersRestoration copy() {
        return new WaterbendersRestoration(this);
    }
}
