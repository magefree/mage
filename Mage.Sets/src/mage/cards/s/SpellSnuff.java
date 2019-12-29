package mage.cards.s;

import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellSnuff extends CardImpl {

    public SpellSnuff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // Fateful hour — If you have 5 or less life, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), FatefulHourCondition.instance,
                "<br><i>Fateful hour</i> &mdash; If you have 5 or less life, draw a card."
        ));
    }

    private SpellSnuff(final SpellSnuff card) {
        super(card);
    }

    @Override
    public SpellSnuff copy() {
        return new SpellSnuff(this);
    }
}
