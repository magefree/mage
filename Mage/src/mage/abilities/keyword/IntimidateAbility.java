package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class IntimidateAbility extends EvasionAbility<IntimidateAbility> {
    private static final IntimidateAbility fInstance = new IntimidateAbility();

    public static IntimidateAbility getInstance() {
        return fInstance;
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
        return fInstance;
    }
}

class IntimidateEffect extends RestrictionEffect<IntimidateEffect> {
    public IntimidateEffect() {
        super(Constants.Duration.WhileOnBattlefield);
    }

    public IntimidateEffect(final IntimidateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAbilities().containsKey(IntimidateAbility.getInstance().getId())) {
			return true;
		}
		return false;
    }

    @Override
	public boolean canBlock(Permanent attacker, Permanent blocker, Game game) {
        boolean result = false;
		if (blocker.getCardType().contains(Constants.CardType.ARTIFACT) && (blocker.getCardType().contains(Constants.CardType.CREATURE)))
			result = true;
        if (attacker.getColor().shares(blocker.getColor()))
            result = true;
		return result;
	}

    @Override
    public IntimidateEffect copy() {
        return new IntimidateEffect(this);
    }
}