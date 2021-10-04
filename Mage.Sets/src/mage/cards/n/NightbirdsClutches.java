
package mage.cards.n;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class NightbirdsClutches extends CardImpl {

    public NightbirdsClutches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Up to two target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{R}")));
    }

    private NightbirdsClutches(final NightbirdsClutches card) {
        super(card);
    }

    @Override
    public NightbirdsClutches copy() {
        return new NightbirdsClutches(this);
    }
}
