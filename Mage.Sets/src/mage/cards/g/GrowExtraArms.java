package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrowExtraArms extends CardImpl {

    private static final Condition condition
            = new SourceTargetsPermanentCondition(new FilterPermanent(SubType.SPIDER, "a Spider"));

    public GrowExtraArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // This spell costs {1} less to cast if it targets a Spider.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GrowExtraArms(final GrowExtraArms card) {
        super(card);
    }

    @Override
    public GrowExtraArms copy() {
        return new GrowExtraArms(this);
    }
}
