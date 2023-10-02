
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DinrovaHorror extends CardImpl {

    public DinrovaHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Dinrova Horror enters the battlefield, return target permanent to its owner's hand, then that player discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DinrovaHorrorEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

    }

    private DinrovaHorror(final DinrovaHorror card) {
        super(card);
    }

    @Override
    public DinrovaHorror copy() {
        return new DinrovaHorror(this);
    }
}

class DinrovaHorrorEffect extends OneShotEffect {

    public DinrovaHorrorEffect() {
        super(Outcome.Detriment);
        this.staticText = "return target permanent to its owner's hand, then that player discards a card";
    }

    private DinrovaHorrorEffect(final DinrovaHorrorEffect effect) {
        super(effect);
    }

    @Override
    public DinrovaHorrorEffect copy() {
        return new DinrovaHorrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            Player controller = game.getPlayer(target.getControllerId());
            if (controller != null) {
                controller.moveCards(target, Zone.HAND, source, game);
                controller.discard(1, false, false, source, game);
                return true;
            }
        }
        return false;
    }
}
