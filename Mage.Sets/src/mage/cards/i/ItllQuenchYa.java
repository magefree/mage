package mage.cards.i;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ItllQuenchYa extends CardImpl {

    public ItllQuenchYa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.subtype.add(SubType.LESSON);

        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ItllQuenchYa(final ItllQuenchYa card) {
        super(card);
    }

    @Override
    public ItllQuenchYa copy() {
        return new ItllQuenchYa(this);
    }
}
