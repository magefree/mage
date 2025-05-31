package mage.abilities.common;

import mage.*;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        return player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (objects.isEmpty()) {
            discard();
            return false;
        }
        for (MageObject object : objects) {
            if (object instanceof Permanent) {
                Permanent permanent = (Permanent) object;
                permanent.retainAllEnchantmentSubTypes(game);
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ENCHANTMENT);
            }
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }

    @Override
    public EnduringGlimmerTypeEffect copy() {
        return new EnduringGlimmerTypeEffect(this);
    }
}
