package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.NecronWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightScythe extends CardImpl {

    public NightScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Invasion Beams -- When Night Scythe enters the battlefield, create a 2/2 black Necron Warrior artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new NecronWarriorToken())
        ).withFlavorWord("Invasion Beams"));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private NightScythe(final NightScythe card) {
        super(card);
    }

    @Override
    public NightScythe copy() {
        return new NightScythe(this);
    }
}
