package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class DiscordantDirge extends CardImpl {

    private static final String rule = "Look at target opponent's hand and choose up to X cards from it, where X is the number of verse counters on {this}. That player discards those cards..";

    public DiscordantDirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // At the beginning of your upkeep, you may put a verse counter on Discordant Dirge.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));

        // {B}, Sacrifice Discordant Dirge: Look at target opponent's hand and choose up to X cards from it, where X is the number of verse counters on Discordant Dirge. That player discards those cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DiscordantDirgeEffect(),
                new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public DiscordantDirge(final DiscordantDirge card) {
        super(card);
    }

    @Override
    public DiscordantDirge copy() {
        return new DiscordantDirge(this);
    }
}

class DiscordantDirgeEffect extends OneShotEffect {

    public DiscordantDirgeEffect() {
        super(Outcome.Benefit);
        staticText = "Look at target opponent's hand and choose up to X cards from it, where X is the number of verse counters on {this}. That player discards those card.";
    }

    public DiscordantDirgeEffect(final DiscordantDirgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent discordantDirge = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (discordantDirge != null) {
            int verseCounters = discordantDirge.getCounters(game).getCount(CounterType.VERSE);
            Player targetOpponent = game.getPlayer(source.getFirstTarget());
            Player controller = game.getPlayer(source.getControllerId());
            if (targetOpponent != null
                    && controller != null) {
                controller.lookAtCards(targetOpponent.getName() + " hand", targetOpponent.getHand(), game);
                TargetCard target = new TargetCard(0, verseCounters, Zone.HAND, new FilterCard());
                target.setNotTarget(true);
                if (controller.choose(Outcome.Benefit, targetOpponent.getHand(), target, game)) {
                    target.getTargets().stream().map(game::getCard).filter((card) -> (card != null
                            &&  targetOpponent.getHand().contains(card.getId()))).forEachOrdered((card) -> {
                                targetOpponent.discard(card, source, game);
                    });
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public DiscordantDirgeEffect copy() {
        return new DiscordantDirgeEffect(this);
    }
}
