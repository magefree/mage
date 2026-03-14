package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MerfolkWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerrowSkyswimmer extends CardImpl {

    public MerrowSkyswimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, create a 1/1 white and blue Merfolk creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MerfolkWhiteBlueToken())));
    }

    private MerrowSkyswimmer(final MerrowSkyswimmer card) {
        super(card);
    }

    @Override
    public MerrowSkyswimmer copy() {
        return new MerrowSkyswimmer(this);
    }
}
