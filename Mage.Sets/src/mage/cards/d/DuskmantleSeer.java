
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DuskmantleSeer extends CardImpl {

    public DuskmantleSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, each player reveals the top card of their library, loses life equal to that card's converted mana cost, then puts it into their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DuskmantleSeerEffect(), TargetController.YOU, false, false));

    }

    public DuskmantleSeer(final DuskmantleSeer card) {
        super(card);
    }

    @Override
    public DuskmantleSeer copy() {
        return new DuskmantleSeer(this);
    }
}

class DuskmantleSeerEffect extends OneShotEffect {

    public DuskmantleSeerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of their library, loses life equal to that card's converted mana cost, then puts it into their hand";
    }

    public DuskmantleSeerEffect(final DuskmantleSeerEffect effect) {
        super(effect);
    }

    @Override
    public DuskmantleSeerEffect copy() {
        return new DuskmantleSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceCard = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        for (Player player : game.getPlayers().values()) {
            if (player.getLibrary().hasCards()) {
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    player.revealCards(source, ": Revealed by " + player.getName(), new CardsImpl(card), game);
                    player.loseLife(card.getConvertedManaCost(), game, false);
                    player.moveCards(card, Zone.HAND, source, game);
                }
            }
        }
        return true;
    }
}
