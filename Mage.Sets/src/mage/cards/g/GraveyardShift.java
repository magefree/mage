package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraveyardShift extends CardImpl {

    public GraveyardShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // This spell has flash as long as there are five or more mana values among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FlashAbility.getInstance(), Duration.WhileOnBattlefield, true
                ), DifferentManaValuesInGraveCondition.FIVE, "this spell has flash " +
                        "as long as there are five or more mana values among cards in your graveyard")
        ).setRuleAtTheTop(true).addHint(DifferentManaValuesInGraveHint.instance));

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private GraveyardShift(final GraveyardShift card) {
        super(card);
    }

    @Override
    public GraveyardShift copy() {
        return new GraveyardShift(this);
    }
}
