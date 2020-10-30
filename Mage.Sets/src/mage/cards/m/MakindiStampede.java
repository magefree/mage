package mage.cards.m;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakindiStampede extends CardImpl {

    public MakindiStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.m.MakindiMesas.class;

        // Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn));
    }

    private MakindiStampede(final MakindiStampede card) {
        super(card);
    }

    @Override
    public MakindiStampede copy() {
        return new MakindiStampede(this);
    }
}
