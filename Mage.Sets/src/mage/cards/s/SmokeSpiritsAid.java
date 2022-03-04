package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SmokeBlessingToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmokeSpiritsAid extends CardImpl {

    public SmokeSpiritsAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // For each of up to X target creatures, create a red Aura enchantment token named Smoke Blessing attached to that creature. Those tokens have enchant creature and "When enchanted creature dies, it deals 1 damage to its controller and you create a Treasure token."
        this.getSpellAbility().addEffect(new SmokeSpiritsAidEffect());
        this.getSpellAbility().setTargetAdjuster(SmokeSpiritsAidAdjuster.instance);
    }

    private SmokeSpiritsAid(final SmokeSpiritsAid card) {
        super(card);
    }

    @Override
    public SmokeSpiritsAid copy() {
        return new SmokeSpiritsAid(this);
    }
}

enum SmokeSpiritsAidAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getManaCostsToPay().getX() > 0) {
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(0, ability.getManaCostsToPay().getX()));
        }
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
        Token token = new SmokeBlessingToken();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            token.putOntoBattlefield(1, game, source);
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent aura = game.getPermanent(tokenId);
                if (aura == null) {
                    continue;
                }
                aura.getAbilities().get(0).getTargets().get(0).add(source.getFirstTarget(), game);
                aura.getAbilities().get(0).getEffects().get(0).apply(game, aura.getAbilities().get(0));
            }
        }
        return true;
    }
}
