package mage.cards.z;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author North
 */
public final class ZealousPersecution extends CardImpl {

    public ZealousPersecution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        this.getSpellAbility().addEffect(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ).setText("until end of turn, creatures you control get +1/+1"));
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(
                -1, -1, Duration.EndOfTurn
        ).setText("and creatures your opponents control get -1/-1"));
    }

    private ZealousPersecution(final ZealousPersecution card) {
        super(card);
    }

    @Override
    public ZealousPersecution copy() {
        return new ZealousPersecution(this);
    }
}
