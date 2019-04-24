package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * 702.13. Intimidate #
 *
 *  702.13a Intimidate is an evasion ability.
 *
 *  702.13b A creature with intimidate can't be blocked except by artifact creatures
 *          and/or creatures that share a color with it. (See rule 509, "Declare Blockers Step.") #
 *
 *  702.13c Multiple instances of intimidate on the same creature are redundant.
 *
 *
 *
 */
public class IntimidateAbility extends EvasionAbility implements MageSingleton  {
    private static final IntimidateAbility instance = new IntimidateAbility();

    public static IntimidateAbility getInstance() {
        return instance;
    }

    private IntimidateAbility() {
        this.addEffect(new IntimidateEffect());
    }

    @Override
    public String getRule() {
        return "Intimidate";
    }

    @Override
    public IntimidateAbility copy() {
        return instance;
    }
}

class IntimidateEffect extends RestrictionEffect implements MageSingleton {
    public IntimidateEffect() {
        super(Duration.EndOfGame);
    }

    public IntimidateEffect(final IntimidateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getAbilities().containsKey(IntimidateAbility.getInstance().getId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        boolean result = false;
        if (blocker.isArtifact() && (blocker.isCreature())) {
            result = true;
        }
        if (attacker.getColor(game).shares(blocker.getColor(game))) {
            result = true;
        }
        return result;
    }

    @Override
    public IntimidateEffect copy() {
        return new IntimidateEffect(this);
    }
}
