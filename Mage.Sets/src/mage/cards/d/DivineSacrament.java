package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Beta_Steward (Honor of the Pure), LevelX2 (Demoralize), cbt
 */
public final class DivineSacrament extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public DivineSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // White creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // Threshold - White creatures get an additional +1/+1 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false),
                ThresholdCondition.instance, "If seven or more cards are in your graveyard, white creatures get an additional +1/+1."
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private DivineSacrament(final DivineSacrament card) {
        super(card);
    }

    @Override
    public DivineSacrament copy() {
        return new DivineSacrament(this);
    }
}
