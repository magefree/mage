package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragToTheRoots extends CardImpl {

    public DragToTheRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{G}");

        // Delirium -- This spell costs {2} less to cast as long as there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, DeliriumCondition.instance)
                .setText("this spell costs {2} less to cast as long as there are four or more card types among cards in your graveyard")
        ).setRuleAtTheTop(true).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // Destroy target nonland permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private DragToTheRoots(final DragToTheRoots card) {
        super(card);
    }

    @Override
    public DragToTheRoots copy() {
        return new DragToTheRoots(this);
    }
}
