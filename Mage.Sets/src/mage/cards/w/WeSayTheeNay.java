package mage.cards.w;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.TargetSpell;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WeSayTheeNay extends CardImpl {

    public WeSayTheeNay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.subtype.add(SubType.ARCANE);

        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Counter target spell unless its controller pays {2}. Counter that spell unless its controller pays {4} instead if this spell was cast using teamwork.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new CounterUnlessPaysEffect(new GenericManaCost(4)),
            new CounterUnlessPaysEffect(new GenericManaCost(2)),
            TeamworkCondition.instance, "counter target spell unless its controller pays {2}. " +
            "Counter that spell unless its controller pays {4} instead if this spell was cast using teamwork"
        ));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private WeSayTheeNay(final WeSayTheeNay card) {
        super(card);
    }

    @Override
    public WeSayTheeNay copy() {
        return new WeSayTheeNay(this);
    }
}
