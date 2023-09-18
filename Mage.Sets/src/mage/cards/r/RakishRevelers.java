package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakishRevelers extends CardImpl {

    public RakishRevelers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When Rakish Revelers enters the battlefield, create a 1/1 green and white Citizen creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CitizenGreenWhiteToken())));

        // {2}, Exile Rakish Revelers from your hand: Target land gains "{T}: Add {R}, {G}, or {W}" until Rakish Revelers is cast from exile. You may cast Rakish Revelers for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("RGW"));
    }

    private RakishRevelers(final RakishRevelers card) {
        super(card);
    }

    @Override
    public RakishRevelers copy() {
        return new RakishRevelers(this);
    }
}
