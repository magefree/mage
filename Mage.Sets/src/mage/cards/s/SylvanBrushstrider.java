package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SylvanBrushstrider extends CardImpl {

    public SylvanBrushstrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Sylvan Brushstrider enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
    }

    private SylvanBrushstrider(final SylvanBrushstrider card) {
        super(card);
    }

    @Override
    public SylvanBrushstrider copy() {
        return new SylvanBrushstrider(this);
    }
}
