
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnSourceFromGraveyardToBattlefieldEffect extends ReturnSourceThenEffect {

    protected final boolean tapped;
    protected final boolean ownerControl;
    private final boolean haste;
    private final boolean attacking;

    public ReturnSourceFromGraveyardToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped) {
        this(tapped, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        this(tapped, ownerControl, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl, boolean haste) {
        this(tapped, ownerControl, haste, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl, boolean haste, boolean attacking) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.ownerControl = ownerControl;
        this.haste = haste;
        this.attacking = attacking;
        this.staticText = setText();
    }

    protected ReturnSourceFromGraveyardToBattlefieldEffect(final ReturnSourceFromGraveyardToBattlefieldEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerControl = effect.ownerControl;
        this.haste = effect.haste;
        this.attacking = effect.attacking;
    }

    @Override
    public ReturnSourceFromGraveyardToBattlefieldEffect copy() {
        return new ReturnSourceFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        Player player;
        if (ownerControl) {
            player = game.getPlayer(card.getOwnerId());
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }
        if (!canReturn(card, game, source)) {
            return true;
        }
        putOntoBattlefield(player, card, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return true;
        }
        if (haste) {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        if (attacking) {
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }
        resolveAdditionalEffects(game, source, permanent);
        return true;
    }

    private boolean canReturn(Card card, Game game, Ability source) {
        return game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                && source.getSourceCardIfItStillExists(game) != null;
    }

    protected void putOntoBattlefield(Player player, Card card, Ability source, Game game) {
        player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null);
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("return this card from your graveyard to the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (attacking) {
            if (tapped) {
                sb.append(" and");
            }
            sb.append(" attacking");
        }
        if (ownerControl) {
            sb.append(" under its owner's control");
        }
        return sb.toString();
    }
}
