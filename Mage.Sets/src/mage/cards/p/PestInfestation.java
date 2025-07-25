package mage.cards.p;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestInfestation extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(GetXValue.instance, 2);

    public PestInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Destroy up to X target artifacts and/or enchantments. Create twice X 1/1 black and green Pest creature tokens with "When this creature dies, you gain 1 life."
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setText("destroy up to X target artifacts and/or enchantments."));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Pest11GainLifeToken(), xValue)
                .setText("Create twice X 1/1 black and green Pest creature tokens with \"When this token dies, you gain 1 life.\""));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private PestInfestation(final PestInfestation card) {
        super(card);
    }

    @Override
    public PestInfestation copy() {
        return new PestInfestation(this);
    }
}
