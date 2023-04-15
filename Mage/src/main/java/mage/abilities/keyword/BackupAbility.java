package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class BackupAbility extends EntersBattlefieldTriggeredAbility {

    private final Card card;
    private final int amount;
    private final List<Ability> abilitiesToAdd = new ArrayList<>();

    public BackupAbility(Card card, int amount) {
        super(null, true);
        this.addEffect(new BackupEffect(amount, abilitiesToAdd));
        this.card = card;
        this.amount = amount;
        this.addTarget(new TargetCreaturePermanent());
    }

    public BackupAbility(final BackupAbility ability) {
        super(ability);
        this.amount = ability.amount;
        this.card = ability.card;
        this.abilitiesToAdd.addAll(ability.abilitiesToAdd);
    }

    @Override
    public BackupAbility copy() {
        return new BackupAbility(this);
    }

    @Override
    public String getRule() {
        return "Backup " + amount + " <i>(When this creature enters the battlefield, " +
                "put a +1/+1 counter on target creature. If that's another creature, " +
                "it gains the following abilit" + (abilitiesToAdd.size() > 1 ? "ies" : "y") + " until end of turn.)</i>";
    }

    public void addAbility(Ability ability) {
        addAbility(ability, null);
    }

    public void addAbility(Ability ability, Watcher watcher) {
        if (watcher != null) {
            ability.addWatcher(watcher);
        }
        card.addAbility(ability);
        abilitiesToAdd.add(ability);
    }

    public boolean hasAbilities() {
        return !abilitiesToAdd.isEmpty();
    }
}

class BackupEffect extends OneShotEffect {

    private final int amount;
    private final List<Ability> abilitiesToAdd = new ArrayList<>();

    public BackupEffect(int amount, List<Ability> abilitiesToAdd) {
        super(Outcome.Detriment);
        this.amount = amount;
        this.abilitiesToAdd.addAll(abilitiesToAdd);
    }

    public BackupEffect(final BackupEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.abilitiesToAdd.addAll(effect.abilitiesToAdd);
    }

    @Override
    public BackupEffect copy() {
        return new BackupEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        if (permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        for (Ability ability : abilitiesToAdd) {
            game.addEffect(new GainAbilityTargetEffect(ability).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
