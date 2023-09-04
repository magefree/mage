package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Rackling extends CardImpl {

    public Rackling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, Rackling deals X damage to that player, where X is 3 minus the number of cards in their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new RacklingEffect(), TargetController.OPPONENT, false, true));
    }

    private Rackling(final Rackling card) {
        super(card);
    }

    @Override
    public Rackling copy() {
        return new Rackling(this);
    }
}

class RacklingEffect extends OneShotEffect {

    public RacklingEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is 3 minus the number of cards in their hand";
    }

    private RacklingEffect(final RacklingEffect effect) {
        super(effect);
    }

    @Override
    public RacklingEffect copy() {
        return new RacklingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = 3 - player.getHand().size();
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), source, game);
            }
            return true;
        }

        return false;
    }
}
