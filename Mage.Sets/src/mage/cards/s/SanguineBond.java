package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author North
 */
public final class SanguineBond extends CardImpl {

    public SanguineBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever you gain life, target opponent loses that much life.
        Ability ability = new GainLifeControllerTriggeredAbility(new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SanguineBond(final SanguineBond card) {
        super(card);
    }

    @Override
    public SanguineBond copy() {
        return new SanguineBond(this);
    }
}
