
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class WilyBandar extends CardImpl {

    public WilyBandar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}: Wily Bandar gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")));
    }

    private WilyBandar(final WilyBandar card) {
        super(card);
    }

    @Override
    public WilyBandar copy() {
        return new WilyBandar(this);
    }
}
