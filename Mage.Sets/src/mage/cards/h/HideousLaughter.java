package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class HideousLaughter extends CardImpl {

    public HideousLaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");
        this.subtype.add(SubType.ARCANE);

        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2,-2, Duration.EndOfTurn));
        // Splice onto Arcane {3}{B}{B}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{3}{B}{B}"));
    }

    private HideousLaughter(final HideousLaughter card) {
        super(card);
    }

    @Override
    public HideousLaughter copy() {
        return new HideousLaughter(this);
    }
}
