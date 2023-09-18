package mage.cards.l;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoseFocus extends CardImpl {

    public LoseFocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Replicate {U}
        this.addAbility(new ReplicateAbility("{U}"));

        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private LoseFocus(final LoseFocus card) {
        super(card);
    }

    @Override
    public LoseFocus copy() {
        return new LoseFocus(this);
    }
}
