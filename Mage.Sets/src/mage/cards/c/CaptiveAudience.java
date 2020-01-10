package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.abilities.effects.common.EntersBattlefieldUnderControlOfOpponentOfChoiceEffect;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class CaptiveAudience extends CardImpl {

    public CaptiveAudience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{R}");

        // Captive Audience enters the battlefield under the control of an opponent of your choice.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldUnderControlOfOpponentOfChoiceEffect()));

        // At the beginning of your upkeep, choose one that hasn't been chosen —
        // • Your life total becomes 4.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new SetPlayerLifeSourceEffect(4), TargetController.YOU, false
        );
        ability.getModes().setEachModeOnlyOnce(true);

        // • Discard your hand.
        ability.addMode(new Mode(new DiscardHandControllerEffect()));

        // • Each opponent creates five 2/2 black Zombie creature tokens.
        ability.addMode(new Mode(new CaptiveAudienceCreateTokensEffect()));
        this.addAbility(ability);
    }

    private CaptiveAudience(final CaptiveAudience card) {
        super(card);
    }

    @Override
    public CaptiveAudience copy() {
        return new CaptiveAudience(this);
    }
}

class CaptiveAudienceCreateTokensEffect extends OneShotEffect {

    CaptiveAudienceCreateTokensEffect() {
        super(Benefit);
        staticText = "Each opponent creates five 2/2 black Zombie creature tokens.";
    }

    private CaptiveAudienceCreateTokensEffect(final CaptiveAudienceCreateTokensEffect effect) {
        super(effect);
    }

    @Override
    public CaptiveAudienceCreateTokensEffect copy() {
        return new CaptiveAudienceCreateTokensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Player player : game.getPlayers().values()) {
            if (player != null && controller.hasOpponent(player.getId(), game)) {
                Effect effect = new CreateTokenTargetEffect(new ZombieToken(), 5);
                effect.setTargetPointer(new FixedTarget(player.getId(), game));
                effect.apply(game, source);
            }
        }
        return true;
    }
}