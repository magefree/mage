package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Viseling extends CardImpl {

    public Viseling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each opponent's upkeep, Viseling deals X damage to that player, where X is the number of cards in their hand minus 4.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ViselingEffect(), TargetController.OPPONENT, false));

    }

    private Viseling(final Viseling card) {
        super(card);
    }

    @Override
    public Viseling copy() {
        return new Viseling(this);
    }
}

class ViselingEffect extends OneShotEffect {

    public ViselingEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals X damage to that player, where X is the number of cards in their hand minus 4";
    }

    private ViselingEffect(final ViselingEffect effect) {
        super(effect);
    }

    @Override
    public ViselingEffect copy() {
        return new ViselingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            int xValue = opponent.getHand().size() - 4;
            if (xValue > 0) {
                opponent.damage(xValue, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
