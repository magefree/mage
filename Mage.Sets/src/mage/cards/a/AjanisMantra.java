package mage.cards.a;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class AjanisMantra extends CardImpl {

    public AjanisMantra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1), true));
    }

    private AjanisMantra(final AjanisMantra card) {
        super(card);
    }

    @Override
    public AjanisMantra copy() {
        return new AjanisMantra(this);
    }
}
