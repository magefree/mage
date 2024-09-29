package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesThatAttackedThisTurnCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class RowdyResearch extends CardImpl {

    public RowdyResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{6}{U}");
        
        // This spell costs {1} less to cast for each creature that attacked this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(CreaturesThatAttackedThisTurnCount.instance)
                .setText("this spell costs {1} less to cast for each creature that attacked this turn")
        ).addHint(CreaturesThatAttackedThisTurnCount.instance.getHint()).setRuleAtTheTop(true));

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private RowdyResearch(final RowdyResearch card) {
        super(card);
    }

    @Override
    public RowdyResearch copy() {
        return new RowdyResearch(this);
    }
}
