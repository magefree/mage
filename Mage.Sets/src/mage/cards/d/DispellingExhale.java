package mage.cards.d;

import mage.abilities.condition.common.BeheldDragonCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.BeholdDragonAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DispellingExhale extends CardImpl {

    public DispellingExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // As an additional cost to cast this spell, you may behold a Dragon.
        this.addAbility(new BeholdDragonAbility());

        // Counter target spell unless its controller pays {2}. If a Dragon was beheld, counter that spell unless its controller pays {4} instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(4)),
                new CounterUnlessPaysEffect(new GenericManaCost(2)),
                BeheldDragonCondition.instance, "counter target spell unless its controller pays {2}. " +
                "If a Dragon was beheld, counter that spell unless its controller pays {4} instead"
        ));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DispellingExhale(final DispellingExhale card) {
        super(card);
    }

    @Override
    public DispellingExhale copy() {
        return new DispellingExhale(this);
    }
}
