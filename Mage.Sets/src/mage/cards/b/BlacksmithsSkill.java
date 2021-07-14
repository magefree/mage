package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlacksmithsSkill extends CardImpl {

    public BlacksmithsSkill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target permanent gains hexproof and indestructible until end of turn. If it's an artifact creature, it gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("target permanent gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and indestructible until end of turn."));
        this.getSpellAbility().addEffect(new BlacksmithsSkillEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private BlacksmithsSkill(final BlacksmithsSkill card) {
        super(card);
    }

    @Override
    public BlacksmithsSkill copy() {
        return new BlacksmithsSkill(this);
    }
}

class BlacksmithsSkillEffect extends OneShotEffect {

    BlacksmithsSkillEffect() {
        super(Outcome.Benefit);
        staticText = "If it's an artifact creature, it gets +2/+2 until end of turn";
    }

    private BlacksmithsSkillEffect(final BlacksmithsSkillEffect effect) {
        super(effect);
    }

    @Override
    public BlacksmithsSkillEffect copy() {
        return new BlacksmithsSkillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.isArtifact(game) && permanent.isCreature(game)) {
            game.addEffect(new BoostTargetEffect(2, 2), source);
            return true;
        }
        return false;
    }
}
