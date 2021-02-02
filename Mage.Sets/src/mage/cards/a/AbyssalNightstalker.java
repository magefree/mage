
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class AbyssalNightstalker extends CardImpl {

    public AbyssalNightstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Abyssal Nightstalker attacks and isn't blocked, defending player discards a card.
        Effect effect = new DiscardTargetEffect(1);
        effect.setText("defending player discards a card");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true));
    }

    private AbyssalNightstalker(final AbyssalNightstalker card) {
        super(card);
    }

    @Override
    public AbyssalNightstalker copy() {
        return new AbyssalNightstalker(this);
    }
}
