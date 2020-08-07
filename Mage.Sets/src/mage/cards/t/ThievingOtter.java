package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievingOtter extends CardImpl {

    public ThievingOtter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Thieving Otter deals damage to an opponent, draw a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ThievingOtter(final ThievingOtter card) {
        super(card);
    }

    @Override
    public ThievingOtter copy() {
        return new ThievingOtter(this);
    }
}
