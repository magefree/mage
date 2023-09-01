
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class MurderOfCrows extends CardImpl {

    public MurderOfCrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever another creature dies, you may draw a card. If you do, discard a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new MurderOfCrowsEffect(), false, true));
    }

    private MurderOfCrows(final MurderOfCrows card) {
        super(card);
    }

    @Override
    public MurderOfCrows copy() {
        return new MurderOfCrows(this);
    }
}

class MurderOfCrowsEffect extends OneShotEffect {

    public MurderOfCrowsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "you may draw a card. If you do, discard a card";
    }

    private MurderOfCrowsEffect(final MurderOfCrowsEffect effect) {
        super(effect);
    }

    @Override
    public MurderOfCrowsEffect copy() {
        return new MurderOfCrowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.chooseUse(Outcome.DrawCard, "Draw a card? If you do, discard a card.", source, game)) {
            if (player.drawCards(1, source, game) > 0) {
                player.discard(1, false, false, source, game);
            }
            return true;
        }
        return false;
    }
}
