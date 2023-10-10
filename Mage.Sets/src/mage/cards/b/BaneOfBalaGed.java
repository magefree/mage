
package mage.cards.b;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        // Whenever Bane of Bala Ged attacks, defending player exiles two permanents they control.
        this.addAbility(new AttacksTriggeredAbility(new BaneOfBalaGedEffect(), false, "", SetTargetPointer.PLAYER));
    }

    private BaneOfBalaGed(final BaneOfBalaGed card) {
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
        this.staticText = "defending player exiles two permanents they control";
    }

    private BaneOfBalaGedEffect(final BaneOfBalaGedEffect effect) {
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
            Set<Card> toExile = target.getTargets().stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            defendingPlayer.moveCards(toExile, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}
