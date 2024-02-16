
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SlipThroughSpace extends CardImpl {

    public SlipThroughSpace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Target creature can't be blocked this turn.
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private SlipThroughSpace(final SlipThroughSpace card) {
        super(card);
    }

    @Override
    public SlipThroughSpace copy() {
        return new SlipThroughSpace(this);
    }
}
