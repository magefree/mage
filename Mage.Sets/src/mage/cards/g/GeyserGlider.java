
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class GeyserGlider extends CardImpl {

    public GeyserGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false));
    }

    private GeyserGlider(final GeyserGlider card) {
        super(card);
    }

    @Override
    public GeyserGlider copy() {
        return new GeyserGlider(this);
    }
}
