package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class IllusoryAmbusher extends CardImpl {

    public IllusoryAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Illusory Ambusher is dealt damage, draw that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY), false));
    }

    private IllusoryAmbusher(final IllusoryAmbusher card) {
        super(card);
    }

    @Override
    public IllusoryAmbusher copy() {
        return new IllusoryAmbusher(this);
    }
}
