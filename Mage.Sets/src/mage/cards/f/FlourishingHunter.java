package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class FlourishingHunter extends CardImpl {

    public FlourishingHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Flourishing Hunter enters the battlefield, you gain life equal to the greatest toughness among other creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainLifeEffect(GreatestAmongPermanentsValue.TOUGHNESS_OTHER_CONTROLLED_CREATURES)
                        .setText("you gain life equal to the greatest toughness among other creatures you control")
        ).addHint(GreatestAmongPermanentsValue.TOUGHNESS_OTHER_CONTROLLED_CREATURES.getHint()));
    }

    private FlourishingHunter(final FlourishingHunter card) {
        super(card);
    }

    @Override
    public FlourishingHunter copy() {
        return new FlourishingHunter(this);
    }
}
