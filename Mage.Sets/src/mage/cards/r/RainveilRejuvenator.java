package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RainveilRejuvenator extends CardImpl {

    public RainveilRejuvenator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When this creature enters, you may mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3), true));

        // {T}: Add an amount of {G} equal to this creature's power.
        this.addAbility(new DynamicManaAbility(
                Mana.GreenMana(1), SourcePermanentPowerValue.NOT_NEGATIVE, "Add an amount of {G} equal to {this}'s power."
        ));
    }

    private RainveilRejuvenator(final RainveilRejuvenator card) {
        super(card);
    }

    @Override
    public RainveilRejuvenator copy() {
        return new RainveilRejuvenator(this);
    }
}
