package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * 702.165. Backup
 * <p>
 * 702.165a Backup is a triggered ability. “Backup N” means “When this creature enters the battlefield,
 * put N +1/+1 counters on target creature. If that’s another creature, it also gains the non-backup
 * abilities of this creature printed below this one until end of turn.” Cards with backup have one or more
 * abilities printed after the backup ability. (Some cards with backup also have abilities printed before
 * the backup ability.)
 * <p>
 * 702.165b If a permanent enters the battlefield as a copy of a permanent with a backup ability or a token
 * is created that is a copy of that permanent, the order of abilities printed on it is maintained.
 * <p>
 * 702.165c Only abilities printed on the object with backup are granted by its backup ability. Any abilities
 * gained by a permanent, whether due to a copy effect, an effect that grants an ability to a permanent, or an
 * effect that creates a token with certain abilities, are not granted by a backup ability.
 * <p>
 * 702.165d The abilities that a backup ability grants are determined as the ability is put on the stack.
 * They won’t change if the permanent with backup loses any abilities after the ability is put on the stack
 * but before it resolves.
 *
 * @author TheElk801
 */
public class BackupAbility extends EntersBattlefieldTriggeredAbility {

    private final Card card;
    private final int amount;
    private final List<Ability> abilitiesToAdd = new ArrayList<>();

    public BackupAbility(Card card, int amount) {
        super(null, false);
        this.addEffect(new BackupEffect(amount, abilitiesToAdd));
        this.card = card;
        this.amount = amount;
        this.addTarget(new TargetCreaturePermanent());
    }

    protected BackupAbility(final BackupAbility ability) {
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
        addAbility(ability, watcher, false);
    }

    public void addAbility(Ability ability, boolean dontAddToCard) {
        addAbility(ability, null, dontAddToCard);
    }

    /**
     * @param ability
     * @param watcher
     * @param dontAddToCard use it on multiple instances of backups (example: Conclave Sledge-Captain)
     */
    public void addAbility(Ability ability, Watcher watcher, boolean dontAddToCard) {
        // runtime/verify check: wrong duration in backup's static effects
        if (ability instanceof StaticAbility) {
            Effect wrongEffect = ability.getEffects().stream()
                    .filter(effect -> effect instanceof ContinuousEffect)
                    .map(effect -> (ContinuousEffect) effect)
                    .filter(effect -> effect.getDuration().equals(Duration.EndOfTurn))
                    .findFirst()
                    .orElse(null);
            if (wrongEffect != null) {
                // how-to fix: add effect with Duration.EndOfGame (backup's abilities for permanent controls by GainAbility)
                throw new IllegalArgumentException("Wrong code usage. Backup ability and source card must contains static effects. Wrong effect: " + wrongEffect.getClass().getName());
            }
        }

        if (watcher != null) {
            ability.addWatcher(watcher);
        }

        // parent card must have only 1 instance of ability
        if (!dontAddToCard) {
            card.addAbility(ability);
        }

        // target permanent can have multiple instances of ability
        abilitiesToAdd.add(ability);
        CardUtil.castStream(this.getEffects().stream(), BackupEffect.class)
                .forEach(backupEffect -> backupEffect.addAbility(ability));
    }

    public boolean hasAbilities() {
        return !abilitiesToAdd.isEmpty();
    }
}

class BackupEffect extends OneShotEffect {

    private final int amount;
    private final List<Ability> abilitiesToAdd = new ArrayList<>();

    public BackupEffect(int amount, List<Ability> abilitiesToAdd) {
        super(Outcome.BoostCreature);
        this.amount = amount;
        this.abilitiesToAdd.addAll(abilitiesToAdd);
    }

    protected BackupEffect(final BackupEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.abilitiesToAdd.addAll(effect.abilitiesToAdd);
    }

    void addAbility(Ability ability) {
        this.abilitiesToAdd.add(ability);
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
