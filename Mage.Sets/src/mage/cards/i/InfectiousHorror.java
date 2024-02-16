

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class InfectiousHorror extends CardImpl {

    public InfectiousHorror (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new AttacksTriggeredAbility(new InfectiousHorrorEffect(), false));
    }

    private InfectiousHorror(final InfectiousHorror card) {
        super(card);
    }

    @Override
    public InfectiousHorror copy() {
        return new InfectiousHorror(this);
    }
}

class InfectiousHorrorEffect extends OneShotEffect {

    InfectiousHorrorEffect() {
        super(Outcome.Damage);
        staticText = "each opponent loses 2 life";
    }

    private InfectiousHorrorEffect(final InfectiousHorrorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(2, game, source, false);
            }
        }
        return true;
    }

    @Override
    public InfectiousHorrorEffect copy() {
        return new InfectiousHorrorEffect(this);
    }

}