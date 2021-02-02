
package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class KissOfTheAmesha extends CardImpl {

    public KissOfTheAmesha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{U}");

        // Target player gains 7 life and draws two cards.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(7));
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2).setText("and draws two cards"));
    }

    private KissOfTheAmesha(final KissOfTheAmesha card) {
        super(card);
    }

    @Override
    public KissOfTheAmesha copy() {
        return new KissOfTheAmesha(this);
    }
}
