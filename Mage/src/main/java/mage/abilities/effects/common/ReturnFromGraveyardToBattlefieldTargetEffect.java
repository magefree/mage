package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    private final boolean tapped;
    private final boolean attacking;
    // If true, creatures are returned to their owner's control.
    // If false, creatures are returned under the effect's controller control.
    private final boolean underOwnerControl;

    public ReturnFromGraveyardToBattlefieldTargetEffect() {
        this(false);
    }

    public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped) {
        this(tapped, false);
    }
    public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped, boolean attacking) {
        this(tapped, attacking, false);
    }

    public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped, boolean attacking, boolean underOwnerControl) {
        super(Outcome.PutCreatureInPlay);
        this.tapped = tapped;
        this.attacking = attacking;
        this.underOwnerControl = underOwnerControl;
    }

    protected ReturnFromGraveyardToBattlefieldTargetEffect(final ReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
        this.underOwnerControl = effect.underOwnerControl;
    }

    @Override
    public ReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new ReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToMove = new HashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    cardsToMove.add(card);
                }
            }
            controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, tapped, false, underOwnerControl, null);
            if (attacking) {
                for (Card card : cardsToMove) {
                    game.getCombat().addAttackingCreature(card.getId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        boolean yourGrave = !mode.getTargets().isEmpty()
                && mode.getTargets().get(0) instanceof TargetCardInYourGraveyard;
        sb.append(yourGrave ? "return " : "put ");
        if (mode.getTargets().isEmpty()) {
            sb.append("target creature");
        } else {
            Target target = mode.getTargets().get(0);
            if (target.getMaxNumberOfTargets() == Integer.MAX_VALUE
                    && target.getMinNumberOfTargets() == 0) {
                sb.append("any number of ");
            } else if (target.getMaxNumberOfTargets() != target.getNumberOfTargets()) {
                sb.append("up to ");
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
                sb.append(' ');
            } else if (target.getMaxNumberOfTargets() > 1) {
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets()));
                sb.append(' ');
            }
            String targetName = mode.getTargets().get(0).getTargetName();
            if (!targetName.contains("target ")) {
                sb.append("target ");
            }
            sb.append(targetName);
        }
        sb.append(yourGrave ? " to" : " onto");
        sb.append(" the battlefield");
        if (tapped && attacking) {
            sb.append(" tapped and attacking");
        } else if (tapped) {
            sb.append(" tapped");
        } else if (attacking) {
            sb.append(" attacking");
        }
        if (!yourGrave) {
            if (underOwnerControl) {
                sb.append("under their owner's control");
            }
            else {
                sb.append(" under your control");
            }
        }
        return sb.toString();
    }
}
