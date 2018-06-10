
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward
 */
public final class HexParasite extends CardImpl {

    public HexParasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B/P}: Remove up to X counters from target permanent. For each counter removed this way, Hex Parasite gets +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HexParasiteEffect(), new ManaCostsImpl("{X}{B/P}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    public HexParasite(final HexParasite card) {
        super(card);
    }

    @Override
    public HexParasite copy() {
        return new HexParasite(this);
    }
}

class HexParasiteEffect extends OneShotEffect {

    HexParasiteEffect() {
        super(Outcome.Benefit);
        staticText = "Remove up to X counters from target permanent. For each counter removed this way, {this} gets +1/+0 until end of turn";
    }

    HexParasiteEffect(HexParasiteEffect effect) {
        super(effect);
    }

    @Override
    public HexParasiteEffect copy() {
        return new HexParasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetPermanent target = (TargetPermanent) source.getTargets().get(0);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            int toRemove = source.getManaCostsToPay().getX();
            int removed = 0;
            String[] counterNames = permanent.getCounters(game).keySet().toArray(new String[0]);
            for (String counterName : counterNames) {
                if (player.chooseUse(Outcome.Neutral, "Do you want to remove " + counterName + " counters?", source, game)) {
                    if (permanent.getCounters(game).get(counterName).getCount() == 1 || toRemove == 1) {
                        permanent.removeCounters(counterName, 1, game);
                        removed++;
                    } else {
                        int amount = player.getAmount(1, Math.min(permanent.getCounters(game).get(counterName).getCount(), toRemove - removed), "How many?", game);
                        if (amount > 0) {
                            removed += amount;
                            permanent.removeCounters(counterName, amount, game);
                        }
                    }
                }
                if (removed >= toRemove) {
                    break;
                }
            }
            game.addEffect(new BoostSourceEffect(removed, 0, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }

}
