
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class CalculatedDismissal extends CardImpl {

    public CalculatedDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, scry 2.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), SpellMasteryCondition.instance,
                "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, scry 2"));
    }

    private CalculatedDismissal(final CalculatedDismissal card) {
        super(card);
    }

    @Override
    public CalculatedDismissal copy() {
        return new CalculatedDismissal(this);
    }
}
