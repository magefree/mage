package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class JackalPup extends CardImpl {

    public JackalPup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.JACKAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Jackal Pup is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new JackalPupEffect(), false, false));

    }

    private JackalPup(final JackalPup card) {
        super(card);
    }

    @Override
    public JackalPup copy() {
        return new JackalPup(this);
    }
}

class JackalPupEffect extends OneShotEffect {

    public JackalPupEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to you";
    }

    public JackalPupEffect(final JackalPupEffect effect) {
        super(effect);
    }

    @Override
    public JackalPupEffect copy() {
        return new JackalPupEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            you.damage((Integer) this.getValue("damage"), source.getSourceId(), source, game);
        }
        return true;
    }

}
