package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MentalModulation extends CardImpl {

    public MentalModulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // This spell costs {1} less to cast during your turn.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, MyTurnCondition.instance)
                .setText("this spell costs {1} less to cast during your turn"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Tap target artifact or creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private MentalModulation(final MentalModulation card) {
        super(card);
    }

    @Override
    public MentalModulation copy() {
        return new MentalModulation(this);
    }
}
