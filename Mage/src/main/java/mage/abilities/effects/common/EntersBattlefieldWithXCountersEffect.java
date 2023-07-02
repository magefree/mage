package mage.abilities.effects.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Use this effect only (I guess) with EntersBattlefieldAbility like abilities
 *
 * @author LevelX2
 */
public class EntersBattlefieldWithXCountersEffect extends OneShotEffect {

    protected final Counter counter;

    public EntersBattlefieldWithXCountersEffect(Counter counter) {
        super(Outcome.BoostCreature);
        this.counter = counter;
        staticText = "with X " + counter.getName() + " counters on it";
    }

    public EntersBattlefieldWithXCountersEffect(final EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            if (source.getAbilityType() == AbilityType.STATIC) {
                permanent = game.getPermanentEntering(source.getSourceId());
            }
        }
        if (permanent != null) {
            int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
            MageObjectReference mor = new MageObjectReference(source.getSourceId(), zcc, game);
            Map<String, Integer> costTags = game.getPermanentCostsTags().get(mor);
            if (costTags != null){
                int amount = costTags.getOrDefault("X",0);
                if (amount > 0) {
                    Counter counterToAdd = counter.copy();
                    counterToAdd.add(amount - counter.getCount());
                    List<UUID> appliedEffects = (ArrayList<UUID>) this.getValue("appliedEffects");
                    permanent.addCounters(counterToAdd, source.getControllerId(), source, game, appliedEffects);
                }
            }
        }
        return true;
    }

    @Override
    public EntersBattlefieldWithXCountersEffect copy() {
        return new EntersBattlefieldWithXCountersEffect(this);
    }

}
