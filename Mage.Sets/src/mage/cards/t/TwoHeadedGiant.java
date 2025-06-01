
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwoHeadedGiant extends CardImpl {

    public TwoHeadedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Two-Headed Giant attacks, flip two coins.  If both coins come up heads, Two-Headed Giant gains double strike until end of turn.  If both coins come up tails, Two-Headed Giant gains menace until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new TwoHeadedGiantEffect(), false));
    }

    private TwoHeadedGiant(final TwoHeadedGiant card) {
        super(card);
    }

    @Override
    public TwoHeadedGiant copy() {
        return new TwoHeadedGiant(this);
    }
}

class TwoHeadedGiantEffect extends OneShotEffect {

    TwoHeadedGiantEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip two coins. If both coins come up heads, {this} gains double strike until end of turn."
                + " If both coins come up tails, {this} gains menace until end of turn";
    }

    private TwoHeadedGiantEffect(final TwoHeadedGiantEffect effect) {
        super(effect);
    }

    @Override
    public TwoHeadedGiantEffect copy() {
        return new TwoHeadedGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Boolean> flips = player.flipCoins(source, game, 2, false);
        if (flips.get(0) == flips.get(1)) {
            game.addEffect(new GainAbilitySourceEffect(
                    flips.get(0) ? DoubleStrikeAbility.getInstance() : new MenaceAbility(), Duration.EndOfTurn
            ), source);
        }
        return true;
    }
}
