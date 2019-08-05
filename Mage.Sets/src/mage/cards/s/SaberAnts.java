
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.InsectToken;
import mage.players.Player;

/**
 *
 * @author LoneFox
 *
 */
public final class SaberAnts extends CardImpl {

    public SaberAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Saber Ants is dealt damage, you may create that many 1/1 green Insect creature tokens.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new SaberAntsEffect(), true, false, true));
    }

    public SaberAnts(final SaberAnts card) {
        super(card);
    }

    @Override
    public SaberAnts copy() {
        return new SaberAnts(this);
    }
}

class SaberAntsEffect extends OneShotEffect {

    public SaberAntsEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may create that many 1/1 green Insect creature tokens";
    }

    public SaberAntsEffect(final SaberAntsEffect effect) {
        super(effect);
    }

    @Override
    public SaberAntsEffect copy() {
        return new SaberAntsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) this.getValue("damage");
            return new CreateTokenEffect(new InsectToken(), damage).apply(game, source);
        }
        return false;
    }
}
