package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SolidarityOfHeroes extends CardImpl {

    public SolidarityOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Strive - Solidarity of Heroes costs {1}{G} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{1}{G}"));

        // Choose any number of target creatures. Double the number of +1/+1 counters on each of them.
        this.getSpellAbility().addEffect(new SolidarityOfHeroesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private SolidarityOfHeroes(final SolidarityOfHeroes card) {
        super(card);
    }

    @Override
    public SolidarityOfHeroes copy() {
        return new SolidarityOfHeroes(this);
    }
}

class SolidarityOfHeroesEffect extends OneShotEffect {

    public SolidarityOfHeroesEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose any number of target creatures. Double the number of +1/+1 counters on each of them";
    }

    private SolidarityOfHeroesEffect(final SolidarityOfHeroesEffect effect) {
        super(effect);
    }

    @Override
    public SolidarityOfHeroesEffect copy() {
        return new SolidarityOfHeroesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    int existingCounters = permanent.getCounters(game).getCount(CounterType.P1P1);
                    if (existingCounters > 0) {
                        permanent.addCounters(CounterType.P1P1.createInstance(existingCounters), source.getControllerId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
