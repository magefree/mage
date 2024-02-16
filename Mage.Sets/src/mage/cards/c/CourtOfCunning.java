package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CourtOfCunning extends CardImpl {

    public CourtOfCunning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // When Court of Cunning enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        // At the beginning of your upkeep, any number of target players each mill two cards. If you're the monarch, each of those players mills ten cards instead.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CourtOfCunningEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false));
        this.addAbility(ability);
    }

    private CourtOfCunning(final CourtOfCunning card) {
        super(card);
    }

    @Override
    public CourtOfCunning copy() {
        return new CourtOfCunning(this);
    }
}

class CourtOfCunningEffect extends OneShotEffect {

    CourtOfCunningEffect() {
        super(Outcome.Benefit);
        staticText = "any number of target players each mill two cards. " +
                "If you're the monarch, each of those players mills ten cards instead";
    }

    private CourtOfCunningEffect(final CourtOfCunningEffect effect) {
        super(effect);
    }

    @Override
    public CourtOfCunningEffect copy() {
        return new CourtOfCunningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean isMonarch = source.isControlledBy(game.getMonarchId());
        for (Player player : source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())) {
            player.millCards(isMonarch ? 10 : 2, source, game);
        }
        return true;
    }
}
