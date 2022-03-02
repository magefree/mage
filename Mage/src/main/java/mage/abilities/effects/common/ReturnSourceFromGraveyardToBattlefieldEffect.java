
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnSourceFromGraveyardToBattlefieldEffect extends OneShotEffect {

    protected final boolean tapped;
    protected final boolean ownerControl;
    private final boolean haste;

    public ReturnSourceFromGraveyardToBattlefieldEffect() {
        this(false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped) {
        this(tapped, true);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl) {
        this(tapped, ownerControl, false);
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(boolean tapped, boolean ownerControl, boolean haste) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.ownerControl = ownerControl;
        this.haste = haste;
        setText();
    }

    public ReturnSourceFromGraveyardToBattlefieldEffect(final ReturnSourceFromGraveyardToBattlefieldEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.ownerControl = effect.ownerControl;
        this.haste = effect.haste;
    }

    @Override
    public ReturnSourceFromGraveyardToBattlefieldEffect copy() {
        return new ReturnSourceFromGraveyardToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> cards = new ArrayList<>();
        if (source instanceof ZoneChangeTriggeredAbility) {
            for (UUID sourceId : ((ZoneChangeTriggeredAbility) source).getMutatedSourceIds()) {
                cards.add(game.getCard(sourceId));
            }
        }

        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Player player;
        if (ownerControl) {
            player = game.getPlayer(sourceCard.getOwnerId());
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }
        if (cards.isEmpty()) {
            cards.add(sourceCard);
        }
        if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            for (Card card : cards) {
                player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, true, null);
                if (haste) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("return {this} from your graveyard to the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (ownerControl) {
            sb.append(" under its owner's control");
        }
        staticText = sb.toString();
    }

}
