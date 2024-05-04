package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SmokeBlessingToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmokeSpiritsAid extends CardImpl {

    public SmokeSpiritsAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // For each of up to X target creatures, create a red Aura enchantment token named Smoke Blessing attached to that creature. Those tokens have enchant creature and "When enchanted creature dies, it deals 1 damage to its controller and you create a Treasure token."
        this.getSpellAbility().addEffect(new SmokeSpiritsAidEffect());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private SmokeSpiritsAid(final SmokeSpiritsAid card) {
        super(card);
    }

    @Override
    public SmokeSpiritsAid copy() {
        return new SmokeSpiritsAid(this);
    }
}

class SmokeSpiritsAidEffect extends OneShotEffect {

    SmokeSpiritsAidEffect() {
        super(Outcome.Benefit);
        staticText = "for each of up to X target creatures, create a red Aura enchantment token " +
                "named Smoke Blessing attached to that creature. Those tokens have enchant creature and " +
                "\"When enchanted creature dies, it deals 1 damage to its controller and you create a Treasure token.\"";
    }

    private SmokeSpiritsAidEffect(final SmokeSpiritsAidEffect effect) {
        super(effect);
    }

    @Override
    public SmokeSpiritsAidEffect copy() {
        return new SmokeSpiritsAidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            new SmokeBlessingToken().putOntoBattlefield(
                    1, game, source, source.getControllerId(),
                    false, false, null, targetId
            );
        }
        return true;
    }
}
