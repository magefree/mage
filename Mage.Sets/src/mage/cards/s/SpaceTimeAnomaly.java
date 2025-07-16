package mage.cards.s;

import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpaceTimeAnomaly extends CardImpl {

    public SpaceTimeAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{U}");

        // Target player mills cards equal to your life total.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(ControllerLifeCount.instance)
                .setText("target player mills cards equal to your life total"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SpaceTimeAnomaly(final SpaceTimeAnomaly card) {
        super(card);
    }

    @Override
    public SpaceTimeAnomaly copy() {
        return new SpaceTimeAnomaly(this);
    }
}
