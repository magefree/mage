package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakdosTheShowstopper extends CardImpl {

    public RakdosTheShowstopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Rakdos, the Showstopper enters the battlefield, flip a coin for each creature that isn't a Demon, Devil, or Imp. Destroy each creature whose coin comes up tails.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RakdosTheShowstopperEffect(), false));
    }

    private RakdosTheShowstopper(final RakdosTheShowstopper card) {
        super(card);
    }

    @Override
    public RakdosTheShowstopper copy() {
        return new RakdosTheShowstopper(this);
    }
}

class RakdosTheShowstopperEffect extends OneShotEffect {

    RakdosTheShowstopperEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin for each creature that isn't a Demon, Devil, or Imp." +
                " Destroy each creature whose coin comes up tails.";
    }

    private RakdosTheShowstopperEffect(final RakdosTheShowstopperEffect effect) {
        super(effect);
    }

    @Override
    public RakdosTheShowstopperEffect copy() {
        return new RakdosTheShowstopperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent != null
                    && permanent.isCreature(game)
                    && !permanent.hasSubtype(SubType.DEMON, game)
                    && !permanent.hasSubtype(SubType.DEVIL, game)
                    && !permanent.hasSubtype(SubType.IMP, game)
                    && !player.flipCoin(source, game, false)) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }
}