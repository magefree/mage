package mage.cards.d;

import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.RevealPutInHandLoseLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DarkTutelage extends CardImpl {

    public DarkTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new RevealPutInHandLoseLifeEffect()
        ));
    }

    private DarkTutelage(final DarkTutelage card) {
        super(card);
    }

    @Override
    public DarkTutelage copy() {
        return new DarkTutelage(this);
    }
}
