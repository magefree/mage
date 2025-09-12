package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.WarriorToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegnaTheRedeemer extends CardImpl {

    public RegnaTheRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Partner with Krav, the Unredeemed (When this creature enters the battlefield, target player may put Krav into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Krav, the Unredeemed", true));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if your team gained life this turn, create two 1/1 white Warrior creature tokens.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new WarriorToken(), 2),
                false, RegnaTheRedeemerCondition.instance
        ), new PlayerGainedLifeWatcher());
    }

    private RegnaTheRedeemer(final RegnaTheRedeemer card) {
        super(card);
    }

    @Override
    public RegnaTheRedeemer copy() {
        return new RegnaTheRedeemer(this);
    }
}

enum RegnaTheRedeemerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: if teammates are ever implemented this will need to be refactored
        return game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(source.getControllerId()) > 0;
    }

    @Override
    public String toString() {
        return "your team gained life this turn";
    }
}
