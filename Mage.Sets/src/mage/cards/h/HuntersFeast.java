package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class HuntersFeast extends CardImpl {

    public HuntersFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Any number of target players each gain 6 life.
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(6).setText("any number of target players each gain 6 life"));
    }

    private HuntersFeast(final HuntersFeast card) {
        super(card);
    }

    @Override
    public HuntersFeast copy() {
        return new HuntersFeast(this);
    }
}
