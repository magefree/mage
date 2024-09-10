package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.AngelVigilanceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParhelionII extends CardImpl {

    public ParhelionII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Parhelion II attacks, create two 4/4 white Angel creature tokens with flying and vigilance that are attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new AngelVigilanceToken(), 2,
                false, true
        ), false));

        // Crew 4
        this.addAbility(new CrewAbility(4));

    }

    private ParhelionII(final ParhelionII card) {
        super(card);
    }

    @Override
    public ParhelionII copy() {
        return new ParhelionII(this);
    }
}
