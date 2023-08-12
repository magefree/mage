
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author anonymous
 */
public final class BlizzardSpecter extends CardImpl {

    public BlizzardSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Blizzard Specter deals combat damage to a player, choose one
        // - That player returns a permanent they control to its owner's hand;
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ReturnToHandEffect(), false, true);

        // or that player discards a card.
        Mode mode = new Mode(new DiscardTargetEffect(1, false));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private BlizzardSpecter(final BlizzardSpecter card) {
        super(card);
    }

    @Override
    public BlizzardSpecter copy() {
        return new BlizzardSpecter(this);
    }
}

class ReturnToHandEffect extends OneShotEffect {

    public ReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        staticText = "That player returns a permanent they control to its owner's hand";
    }

    public ReturnToHandEffect(final ReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandEffect copy() {
        return new ReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer == null) {
            return false;
        }
        Target target = new TargetControlledPermanent(1, 1, new FilterControlledPermanent(), true);
        if (target.canChoose(targetPlayer.getId(), source, game)) {
            targetPlayer.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                targetPlayer.moveCards(permanent, Zone.HAND, source, game);
            }

        }
        return true;
    }
}
