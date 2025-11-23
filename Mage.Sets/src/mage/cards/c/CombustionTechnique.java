package mage.cards.c;

import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CombustionTechnique extends CardImpl {

    private static final DynamicValue xValue = new IntPlusDynamicValue(
            2, new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON))
    );

    public CombustionTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        this.subtype.add(SubType.LESSON);

        // Combustion Technique deals damage equal to 2 plus the number of Lesson cards in your graveyard to target creature. If that creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to 2 plus the number of Lesson cards in your graveyard to target creature"));
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(LessonsInGraveCondition.getHint());
    }

    private CombustionTechnique(final CombustionTechnique card) {
        super(card);
    }

    @Override
    public CombustionTechnique copy() {
        return new CombustionTechnique(this);
    }
}
