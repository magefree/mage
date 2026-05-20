package mage.cards.p;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PursueThePast extends CardImpl {

    public PursueThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{W}");

        // You gain 2 life. You may discard a card. If you do, draw two cards.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2), new DiscardCardCost()
        ));

        // Flashback {2}{R}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}{W}")));
    }

    private PursueThePast(final PursueThePast card) {
        super(card);
    }

    @Override
    public PursueThePast copy() {
        return new PursueThePast(this);
    }
}
