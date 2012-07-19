package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Split Second
 * As long as this spell is on the stack, players can't cast other spells or activate abilities that aren't mana abilities.
 */
public class SplitSecondAbility extends SimpleStaticAbility implements MageSingleton {
    private static final SplitSecondAbility ability = new SplitSecondAbility();

    public static SplitSecondAbility getInstance() {
        return ability;
    }

    private SplitSecondAbility() {
        super(Constants.Zone.STACK, new SplitSecondEffect());
    }

    @Override
    public String getRule() {
        return "Split second";
    }

    @Override
    public SimpleStaticAbility copy() {
        return ability;
    }
}

class SplitSecondEffect extends ReplacementEffectImpl<SplitSecondEffect> implements MageSingleton {
    SplitSecondEffect() {
        super(Constants.Duration.WhileOnStack, Constants.Outcome.Detriment);
    }

    SplitSecondEffect(final SplitSecondEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY || event.getType() == GameEvent.EventType.CAST_SPELL) {
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SplitSecondEffect copy() {
        return new SplitSecondEffect(this);
    }
}
