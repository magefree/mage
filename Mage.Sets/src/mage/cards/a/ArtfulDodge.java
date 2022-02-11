
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ArtfulDodge extends CardImpl {

    public ArtfulDodge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Target creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Flashback {U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{U}")));
    }

    private ArtfulDodge(final ArtfulDodge card) {
        super(card);
    }

    @Override
    public ArtfulDodge copy() {
        return new ArtfulDodge(this);
    }
}
