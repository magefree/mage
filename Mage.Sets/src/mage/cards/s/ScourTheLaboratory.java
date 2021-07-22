package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class ScourTheLaboratory extends CardImpl {

    public ScourTheLaboratory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // <i>Delirium</i> &mdash; Scour the Laboratory costs {2} less to cast if there are four or more card types among cards in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(2, DeliriumCondition.instance));
        ability.setRuleAtTheTop(true);
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        ability.addHint(CardTypesInGraveyardHint.YOU);
        this.addAbility(ability);

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
    }

    private ScourTheLaboratory(final ScourTheLaboratory card) {
        super(card);
    }

    @Override
    public ScourTheLaboratory copy() {
        return new ScourTheLaboratory(this);
    }
}
