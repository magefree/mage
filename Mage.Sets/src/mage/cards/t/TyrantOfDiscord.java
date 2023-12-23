
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.RandomUtil;

/**
 * @author noxx
 */
public final class TyrantOfDiscord extends CardImpl {

    public TyrantOfDiscord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Tyrant of Discord enters the battlefield, target opponent chooses a permanent they control at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TyrantOfDiscordEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TyrantOfDiscord(final TyrantOfDiscord card) {
        super(card);
    }

    @Override
    public TyrantOfDiscord copy() {
        return new TyrantOfDiscord(this);
    }
}

class TyrantOfDiscordEffect extends OneShotEffect {

    public TyrantOfDiscordEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent chooses a permanent they control at random and sacrifices it. If a nonland permanent is sacrificed this way, repeat this process";
    }

    private TyrantOfDiscordEffect(final TyrantOfDiscordEffect effect) {
        super(effect);
    }

    @Override
    public TyrantOfDiscordEffect copy() {
        return new TyrantOfDiscordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target = source.getFirstTarget();
        Player opponent = game.getPlayer(target);
        if (opponent != null) {
            boolean stop = false;
            while (!stop) {
                int count = game.getBattlefield().countAll(new FilterPermanent(), opponent.getId(), game);
                if (count > 0) {
                    int random = (int)(RandomUtil.nextDouble() * count);
                    int index = 0;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
                        if (index == random) {
                            if (permanent.sacrifice(source, game)) {
                                if (permanent.isLand(game)) {
                                    stop = true;
                                    game.informPlayers("Land permanent has been sacrificed: " + permanent.getName() + ". Stopping process.");
                                } else {
                                    game.informPlayers("Nonland permanent has been sacrificed: " + permanent.getName() + ". Repeating process.");
                                }
                            } else {
                                game.informPlayers("Couldn't sacrifice a permanent. Stopping the process.");
                                stop = true;
                            }
                            break;
                        }
                        index++;
                    }
                } else {
                    game.informPlayers("There is no permanent to sacrifice");
                    stop = true;
                }
            }
        }
        return true;
    }
}
