package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeloheartBike extends CardImpl {

    public VeloheartBike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When this Vehicle enters, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private VeloheartBike(final VeloheartBike card) {
        super(card);
    }

    @Override
    public VeloheartBike copy() {
        return new VeloheartBike(this);
    }
}
