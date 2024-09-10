package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsumingSepulcher extends CardImpl {

    public ConsumingSepulcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;
        this.color.setBlack(true);

        // At the beginning of your upkeep, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeOpponentsEffect(1), TargetController.YOU, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ConsumingSepulcher(final ConsumingSepulcher card) {
        super(card);
    }

    @Override
    public ConsumingSepulcher copy() {
        return new ConsumingSepulcher(this);
    }
}
