package mage.cards.c;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrushDissent extends CardImpl {

    public CrushDissent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Counter target spell unless its controller pays {2}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(2)));
        this.getSpellAbility().addTarget(new TargetSpell());

        // Amass 2.
        this.getSpellAbility().addEffect(new AmassEffect(2, SubType.ZOMBIE));
    }

    private CrushDissent(final CrushDissent card) {
        super(card);
    }

    @Override
    public CrushDissent copy() {
        return new CrushDissent(this);
    }
}
