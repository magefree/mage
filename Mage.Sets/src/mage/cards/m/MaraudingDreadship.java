package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaraudingDreadship extends CardImpl {

    public MaraudingDreadship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Marauding Dreadship enters the battlefield, incubate 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(2)));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MaraudingDreadship(final MaraudingDreadship card) {
        super(card);
    }

    @Override
    public MaraudingDreadship copy() {
        return new MaraudingDreadship(this);
    }
}
