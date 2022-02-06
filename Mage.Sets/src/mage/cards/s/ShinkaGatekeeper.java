package mage.cards.s;

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
 * @author LevelX2
 */
public final class ShinkaGatekeeper extends CardImpl {

    public ShinkaGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Shinka Gatekeeper is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new ShinkaGatekeeperDealDamageEffect(), false, false));
    }

    private ShinkaGatekeeper(final ShinkaGatekeeper card) {
        super(card);
    }

    @Override
    public ShinkaGatekeeper copy() {
        return new ShinkaGatekeeper(this);
    }
}

class ShinkaGatekeeperDealDamageEffect extends OneShotEffect {

    public ShinkaGatekeeperDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to you";
    }

    public ShinkaGatekeeperDealDamageEffect(final ShinkaGatekeeperDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public ShinkaGatekeeperDealDamageEffect copy() {
        return new ShinkaGatekeeperDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
