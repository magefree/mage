package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class NulltreadGargantuan extends CardImpl {

    public NulltreadGargantuan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Nulltread Gargantuan enters the battlefield, put a creature you control on top of its owner's library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NulltreadGargantuanEffect()));
    }

    public NulltreadGargantuan(final NulltreadGargantuan card) {
        super(card);
    }

    @Override
    public NulltreadGargantuan copy() {
        return new NulltreadGargantuan(this);
    }
}

class NulltreadGargantuanEffect extends OneShotEffect {

    NulltreadGargantuanEffect() {
        super(Outcome.UnboostCreature);
        staticText = "put a creature you control on top of its owner's library";
    }

    private NulltreadGargantuanEffect(final NulltreadGargantuanEffect effect) {
        super(effect);
    }

    @Override
    public NulltreadGargantuanEffect copy() {
        return new NulltreadGargantuanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getState().getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && player.putCardsOnTopOfLibrary(permanent, game, source, false);
    }
}
