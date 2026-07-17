package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TheVoidToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheSentryGoldenGuardian extends CardImpl {

    public TheSentryGoldenGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // When The Sentry enters, target opponent creates The Void, a legendary 5/5 black Horror Villain creature token with flying, indestructible, and "The Void attacks each combat if able."
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new TheVoidToken()));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TheSentryGoldenGuardian(final TheSentryGoldenGuardian card) {
        super(card);
    }

    @Override
    public TheSentryGoldenGuardian copy() {
        return new TheSentryGoldenGuardian(this);
    }
}
