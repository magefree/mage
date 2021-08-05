package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Pest11GainLifeToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestInfestation extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public PestInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Destroy up to X target artifacts and/or enchantments. Create twice X 1/1 black and green Pest creature tokens with "When this creature dies, you gain 1 life."
        this.getSpellAbility().addEffect(new DestroyTargetEffect()
                .setText("destroy up to X target artifacts and/or enchantments."));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Pest11GainLifeToken(), xValue)
                .setText("Create twice X 1/1 black and green Pest creature tokens with \"When this creature dies, you gain 1 life.\""));
        this.getSpellAbility().setTargetAdjuster(PestInfestationAdjuster.instance);
    }

    private PestInfestation(final PestInfestation card) {
        super(card);
    }

    @Override
    public PestInfestation copy() {
        return new PestInfestation(this);
    }
}

enum PestInfestationAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(
                0, ability.getManaCostsToPay().getX(),
                StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, false
        ));
    }
}
