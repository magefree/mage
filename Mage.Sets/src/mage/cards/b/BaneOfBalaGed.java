
package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class BaneOfBalaGed extends CardImpl {

    public BaneOfBalaGed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Whenever Bane of Bala Ged attacks, defending player exiles two permanents he or she controls.
        this.addAbility(new AttacksTriggeredAbility(new BaneOfBalaGedEffect(), false, "", SetTargetPointer.PLAYER));
    }

    public BaneOfBalaGed(final BaneOfBalaGed card) {
        super(card);
    }

    @Override
    public BaneOfBalaGed copy() {
        return new BaneOfBalaGed(this);
    }
}

class BaneOfBalaGedEffect extends OneShotEffect {

    public BaneOfBalaGedEffect() {
        super(Outcome.Benefit);
        this.staticText = "defending player exiles two permanents he or she controls";
    }

    public BaneOfBalaGedEffect(final BaneOfBalaGedEffect effect) {
        super(effect);
    }

    @Override
    public BaneOfBalaGedEffect copy() {
        return new BaneOfBalaGedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (defendingPlayer != null) {
            Target target = new TargetControlledPermanent(2);
            defendingPlayer.chooseTarget(outcome, target, source, game);
            Set<Card> toExile = new HashSet<>();
            target.getTargets().stream().map((targetId)
                    -> game.getPermanent(targetId)).filter((permanent)
                    -> (permanent != null)).forEach((permanent)
                    -> {
                toExile.add(permanent);
            });
            defendingPlayer.moveCards(toExile, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
