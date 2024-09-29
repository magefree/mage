
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.keyword.ExploreTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EnterTheUnknown extends CardImpl {

    public EnterTheUnknown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target creature you control explores.
        this.getSpellAbility().addEffect(new ExploreTargetEffect()
                .setText("Target creature you control explores. <i>(Reveal the top card of your library. Put that card into your hand if it's a land. Otherwise, put a +1/+1 counter on this creature, then put the card back or put it into your graveyard.)</i>"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // You may play an additional land this turn.
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn).concatBy("<br>"));

    }

    private EnterTheUnknown(final EnterTheUnknown card) {
        super(card);
    }

    @Override
    public EnterTheUnknown copy() {
        return new EnterTheUnknown(this);
    }
}
