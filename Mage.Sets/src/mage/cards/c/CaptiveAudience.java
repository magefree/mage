package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.EntersBattlefieldUnderControlOfOpponentOfChoiceEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.ZombieToken;

import java.util.UUID;

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
        ability.addMode(new Mode(new CreateTokenAllEffect(new ZombieToken(), 5, TargetController.OPPONENT)));
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
