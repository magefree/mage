package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class FreneticEfreet extends CardImpl {

    public FreneticEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.subtype.add(SubType.EFREET);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {0}: Flip a coin. If you win the flip, Frenetic Efreet phases out. If you lose the flip, sacrifice Frenetic Efreet.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new FreneticEfreetEffect(),
                new GenericManaCost(0)
        ));
    }

    private FreneticEfreet(final FreneticEfreet card) {
        super(card);
    }

    @Override
    public FreneticEfreet copy() {
        return new FreneticEfreet(this);
    }
}

class FreneticEfreetEffect extends OneShotEffect {

    public FreneticEfreetEffect() {
        super(Outcome.Neutral);
        staticText = "Flip a coin. If you win the flip, "
                + "{this} phases out. If you lose the flip, sacrifice {this}";
    }

    private FreneticEfreetEffect(final FreneticEfreetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean flip = controller.flipCoin(source, game, true);
        if (permanent == null) {
            return false;
        }
        if (flip) {
            return permanent.phaseOut(game);
        } else {
            permanent.sacrifice(source, game);
            return true;
        }
    }

    @Override
    public FreneticEfreetEffect copy() {
        return new FreneticEfreetEffect(this);
    }
}
