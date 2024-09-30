package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801, PurpleCrowbar
 */
public class EnduringGlimmerTriggeredAbility extends DiesSourceTriggeredAbility {

    public EnduringGlimmerTriggeredAbility() {
        super(new EnduringGlimmerReturnEffect());
    }

    private EnduringGlimmerTriggeredAbility(final EnduringGlimmerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnduringGlimmerTriggeredAbility copy() {
        return new EnduringGlimmerTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent permanent = (Permanent) this.getEffects().get(0).getValue("permanentLeftBattlefield");
        return permanent != null && permanent.isCreature(game);
    }
}

class EnduringGlimmerReturnEffect extends OneShotEffect {

    EnduringGlimmerReturnEffect() {
        super(Outcome.Benefit);
        staticText = "if it was a creature, return it to the battlefield under its owner's control. It's an enchantment";
    }

    private EnduringGlimmerReturnEffect(final EnduringGlimmerReturnEffect effect) {
        super(effect);
    }

    @Override
    public EnduringGlimmerReturnEffect copy() {
        return new EnduringGlimmerReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        game.addEffect(new EnduringGlimmerTypeEffect()
                .setTargetPointer(new FixedTarget(new MageObjectReference(card, game, 1))), source);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class EnduringGlimmerTypeEffect extends ContinuousEffectImpl {

    EnduringGlimmerTypeEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    private EnduringGlimmerTypeEffect(final EnduringGlimmerTypeEffect effect) {
        super(effect);
    }

    @Override
    public EnduringGlimmerTypeEffect copy() {
        return new EnduringGlimmerTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.retainAllEnchantmentSubTypes(game);
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, CardType.ENCHANTMENT);
        return true;
    }
}
