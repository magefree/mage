package mage.cards.w;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarhornBlast extends CardImpl {

    public WarhornBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));

        // Foretell {2}{W}
        this.addAbility(new ForetellAbility(this, "{2}{W}"));
    }

    private WarhornBlast(final WarhornBlast card) {
        super(card);
    }

    @Override
    public WarhornBlast copy() {
        return new WarhornBlast(this);
    }
}
