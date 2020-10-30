
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class ReduceRubble extends SplitCard {

    public ReduceRubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{2}{U}", "{2}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Reduce
        // Counter target spell unless its controller pays {3}.
        getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());
        getLeftHalfCard().getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));

        // Rubble
        // Up to three target lands don't untap during their controller's next untap step.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        Effect effect = new DontUntapInControllersNextUntapStepTargetEffect();
        effect.setText("Up to three target lands don't untap during their controller's next untap step");
        getRightHalfCard().getSpellAbility().addEffect(effect);
        getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(0, 3, new FilterLandPermanent(), false));
    }

    public ReduceRubble(final ReduceRubble card) {
        super(card);
    }

    @Override
    public ReduceRubble copy() {
        return new ReduceRubble(this);
    }
}
