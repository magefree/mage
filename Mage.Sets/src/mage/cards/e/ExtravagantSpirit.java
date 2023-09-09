package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class ExtravagantSpirit extends CardImpl {

    public ExtravagantSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice Extravagant Spirit unless you pay {1} for each card in your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ExtravagantSpiritEffect(), TargetController.YOU, false));
    }

    private ExtravagantSpirit(final ExtravagantSpirit card) {
        super(card);
    }

    @Override
    public ExtravagantSpirit copy() {
        return new ExtravagantSpirit(this);
    }
}

class ExtravagantSpiritEffect extends OneShotEffect {

    public ExtravagantSpiritEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "sacrifice {this} unless you pay {1} for each card in your hand";
    }

    private ExtravagantSpiritEffect(final ExtravagantSpiritEffect effect) {
        super(effect);
    }

    @Override
    public ExtravagantSpiritEffect copy() {
        return new ExtravagantSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return new SacrificeSourceUnlessPaysEffect(
                new GenericManaCost(player.getHand().size())
        ).apply(game, source);
    }
}
