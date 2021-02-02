
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class DemonicDread extends CardImpl {

    public DemonicDread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{R}");

        // Cascade
        this.addAbility(new CascadeAbility());

        // Target creature can't block this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));

    }

    private DemonicDread(final DemonicDread card) {
        super(card);
    }

    @Override
    public DemonicDread copy() {
        return new DemonicDread(this);
    }
}
