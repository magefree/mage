package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.token.RedWarriorToken;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WithinRange extends CardImpl {

    public WithinRange(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // When this enchantment enters, create two 1/1 red Warrior creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedWarriorToken(), 2)));

        // Whenever you attack, each opponent loses life equal to the number of creatures attacking them.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new WithinRangeEffect(), 1));
    }

    private WithinRange(final WithinRange card) {
        super(card);
    }

    @Override
    public WithinRange copy() {
        return new WithinRange(this);
    }
}

class WithinRangeEffect extends OneShotEffect {

    WithinRangeEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses life equal to the number of creatures attacking them";
    }

    private WithinRangeEffect(final WithinRangeEffect effect) {
        super(effect);
    }

    @Override
    public WithinRangeEffect copy() {
        return new WithinRangeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int count = game
                    .getCombat()
                    .getGroups()
                    .stream()
                    .filter(combatGroup -> player.getId().equals(combatGroup.getDefenderId()))
                    .map(CombatGroup::getAttackers)
                    .mapToInt(List::size)
                    .sum();
            player.loseLife(count, game, source, false);
        }
        return true;
    }
}
