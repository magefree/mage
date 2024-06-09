package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class PirsWhim extends CardImpl {

    public PirsWhim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // For each player, choose friend or foe. Each friend searches their library for a land card, puts it on the battlefield tapped, then shuffles their library. Each foe sacrifices an artifact or enchantment they control.
        this.getSpellAbility().addEffect(new PirsWhimEffect());
    }

    private PirsWhim(final PirsWhim card) {
        super(card);
    }

    @Override
    public PirsWhim copy() {
        return new PirsWhim(this);
    }
}

class PirsWhimEffect extends OneShotEffect {

    PirsWhimEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. "
                + "Each friend searches their library for a land card, "
                + "puts it onto the battlefield tapped, then shuffles. "
                + "Each foe sacrifices an artifact or enchantment they control.";
    }

    private PirsWhimEffect(final PirsWhimEffect effect) {
        super(effect);
    }

    @Override
    public PirsWhimEffect copy() {
        return new PirsWhimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (controller != null && !choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        for (Player player : choice.getFriends()) {
            if (player != null) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_LAND);
                if (player.searchLibrary(target, source, game)) {
                    player.moveCards(new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
                    player.shuffleLibrary(source, game);
                }
            }
        }
        for (Player player : choice.getFoes()) {
            if (player != null) {
                Effect effect = new SacrificeEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, 1, "");
                effect.setTargetPointer(new FixedTarget(player.getId(), game));
                effect.apply(game, source);
            }
        }
        return true;
    }
}
