package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author North
 */
public final class ConcussiveBolt extends CardImpl {

    public ConcussiveBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Concussive Bolt deals 4 damage to target player.
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));

        // <i>Metalcraft</i> &mdash; If you control three or more artifacts, creatures that player controls can't block this turn.
        this.getSpellAbility().addEffect(new ConcussiveBoltEffect());
        this.getSpellAbility().addEffect(new ConcussiveBoltRestrictionEffect());
        this.getSpellAbility().addHint(MetalcraftHint.instance);
    }

    private ConcussiveBolt(final ConcussiveBolt card) {
        super(card);
    }

    @Override
    public ConcussiveBolt copy() {
        return new ConcussiveBolt(this);
    }
}

class ConcussiveBoltEffect extends OneShotEffect {

    public ConcussiveBoltEffect() {
        super(Outcome.Benefit);
        this.staticText = "<br><i>Metalcraft</i> &mdash; If you control three or more artifacts, creatures controlled by that player or by that planeswalker's controller can't block this turn.";
    }

    private ConcussiveBoltEffect(final ConcussiveBoltEffect effect) {
        super(effect);
    }

    @Override
    public ConcussiveBoltEffect copy() {
        return new ConcussiveBoltEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getEffects().get(2).setValue("MetalcraftConcussiveBolt", MetalcraftCondition.instance.apply(game, source));
        return true;
    }
}

class ConcussiveBoltRestrictionEffect extends RestrictionEffect {

    public ConcussiveBoltRestrictionEffect() {
        super(Duration.EndOfTurn);
    }

    private ConcussiveBoltRestrictionEffect(final ConcussiveBoltRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public ConcussiveBoltRestrictionEffect copy() {
        return new ConcussiveBoltRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        boolean metalcraft = (Boolean) this.getValue("MetalcraftConcussiveBolt");
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        return metalcraft && permanent.isControlledBy(player.getId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }
}
