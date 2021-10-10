package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;

/**
 *
 * @author nantuko
 */
public final class GnawToTheBone extends CardImpl {

    public GnawToTheBone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // You gain 2 life for each creature card in your graveyard.
        DynamicValue value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE, 2);
        this.getSpellAbility().addEffect(new GainLifeEffect(value));

        // Flashback {2}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{G}")));
    }

    private GnawToTheBone(final GnawToTheBone card) {
        super(card);
    }

    @Override
    public GnawToTheBone copy() {
        return new GnawToTheBone(this);
    }
}
