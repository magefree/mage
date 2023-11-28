
package mage.cards.s;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.RandomUtil;

/**
 *
 * @author spjspj
 */
public final class SeasonsBeatings extends CardImpl {

    public SeasonsBeatings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{R}{R}");

        // Family gathering - Each creature target player controls deals damage equal to its power to another random creature that player controls.
        this.getSpellAbility().addEffect(new SeasonsBeatingsEffect());
        this.getSpellAbility().withFlavorWord("Family gathering");
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private SeasonsBeatings(final SeasonsBeatings card) {
        super(card);
    }

    @Override
    public SeasonsBeatings copy() {
        return new SeasonsBeatings(this);
    }
}

class SeasonsBeatingsEffect extends OneShotEffect {

    SeasonsBeatingsEffect() {
        super(Outcome.Damage);
        staticText = "Each creature target player controls deals damage equal to its power to another random creature that player controls";
    }

    private SeasonsBeatingsEffect(final SeasonsBeatingsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            Map<Integer, UUID> creatures = new HashMap<>();
            int numCreature = 0;

            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                if (permanent != null) {
                    creatures.put(numCreature, permanent.getId());
                    numCreature++;
                }
            }
            if (numCreature < 2) {
                return true;
            }
            for (Integer i : creatures.keySet()) {
                Permanent creature = game.getPermanent(creatures.get(i));

                int other = RandomUtil.nextInt(numCreature);
                while (other == i) {
                    other = RandomUtil.nextInt(numCreature);
                }
                Permanent creature2 = game.getPermanent(creatures.get(other));
                if (creature != null && creature2 != null) {
                    creature2.damage(creature.getPower().getValue(), creature.getId(), source, game, false, true);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public SeasonsBeatingsEffect copy() {
        return new SeasonsBeatingsEffect(this);
    }
}
