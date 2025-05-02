package mage.cards.e;

import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author noxx
 */
public final class ExquisiteBlood extends CardImpl {

    public ExquisiteBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // Whenever an opponent loses life, you gain that much life.
        this.addAbility(new LoseLifeTriggeredAbility(new GainLifeEffect(SavedLifeLossValue.MUCH),
                TargetController.OPPONENT, false, false));
    }

    private ExquisiteBlood(final ExquisiteBlood card) {
        super(card);
    }

    @Override
    public ExquisiteBlood copy() {
        return new ExquisiteBlood(this);
    }
}
