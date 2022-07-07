package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class ClassLevelAbility extends ActivatedAbilityImpl {

    private final int level;
    private final String manaString;

    public ClassLevelAbility(int level, String manaString) {
        super(Zone.BATTLEFIELD, new SetClassLevelEffect(level), new ManaCostsImpl<>(manaString));
        this.level = level;
        this.manaString = manaString;
        setTiming(TimingRule.SORCERY);
    }

    private ClassLevelAbility(final ClassLevelAbility ability) {
        super(ability);
        this.level = ability.level;
        this.manaString = ability.manaString;
    }

    @Override
    public ClassLevelAbility copy() {
        return new ClassLevelAbility(this);
    }

    @Override
    public String getRule() {
        return manaString + ": Level " + level;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.getClassLevel() == level - 1) {
            return super.canActivate(playerId, game);
        }
        return ActivationStatus.getFalse();
    }
}

class SetClassLevelEffect extends OneShotEffect {

    private final int level;

    SetClassLevelEffect(int level) {
        super(Outcome.Benefit);
        this.level = level;
        staticText = "level up to " + level;
    }

    private SetClassLevelEffect(final SetClassLevelEffect effect) {
        super(effect);
        this.level = effect.level;
    }

    @Override
    public SetClassLevelEffect copy() {
        return new SetClassLevelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.isPhasedIn()) {
            return false;
        }

        int oldLevel = permanent.getClassLevel();
        if (!permanent.setClassLevel(level)) {
            return false;
        }

        game.informPlayers(permanent.getLogName() + " levelled up from " + oldLevel + " to " + permanent.getClassLevel());

        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.GAINS_CLASS_LEVEL, source.getSourceId(),
                source, source.getControllerId(), level
        ));
        return true;
    }
}
