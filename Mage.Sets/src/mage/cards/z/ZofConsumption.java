package mage.cards.z;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZofConsumption extends CardImpl {

    public ZofConsumption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.z.ZofBloodbog.class;

        // Each opponent loses 4 life and you gain 4 life.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4).concatBy("and"));
    }

    private ZofConsumption(final ZofConsumption card) {
        super(card);
    }

    @Override
    public ZofConsumption copy() {
        return new ZofConsumption(this);
    }
}
