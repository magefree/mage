package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainControlAllOwnedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierLifelinkToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrostaniDiscordant extends CardImpl {

    public TrostaniDiscordant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, true
        )));

        // When Trostani Discordant enters the battlefield, create two 1/1 white Soldier creature tokens with lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SoldierLifelinkToken(), 2)
        ));

        // At the beginning of your end step, each player gains control of all creatures they own.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new GainControlAllOwnedEffect(StaticFilters.FILTER_PERMANENT_CREATURES),
                TargetController.YOU, false
        ));
    }

    private TrostaniDiscordant(final TrostaniDiscordant card) {
        super(card);
    }

    @Override
    public TrostaniDiscordant copy() {
        return new TrostaniDiscordant(this);
    }
}
