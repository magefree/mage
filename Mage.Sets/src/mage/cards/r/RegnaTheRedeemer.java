
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.WarriorToken;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
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
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new WarriorToken(), 2),
                        TargetController.ANY,
                        false
                ),
                new RegnaTheRedeemerCondition(),
                "At the beginning of each end step, "
                + "if your team gained life this turn, "
                + "create two 1/1 white Warrior creature tokens"
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

class RegnaTheRedeemerCondition extends IntCompareCondition {

    public RegnaTheRedeemerCondition() {
        super(ComparisonType.MORE_THAN, 0);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        int gainedLife = 0;
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        if (watcher != null) {
            for (UUID playerId : game.getPlayerList()) {
                Player player = game.getPlayer(playerId);
                if (player != null && !player.hasOpponent(source.getControllerId(), game)) {
                    gainedLife = watcher.getLifeGained(playerId);
                    if (gainedLife > 0) {
                        break;
                    }
                }
            }
        }
        return gainedLife;
    }
}
