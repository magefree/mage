package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MiasmaDemon extends CardImpl {

    public MiasmaDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Miasma Demon enters, you may discard any number of cards. When you do, up to that many target creatures each get -2/-2 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MiasmaDemonEffect()));
    }

    private MiasmaDemon(final MiasmaDemon card) {
        super(card);
    }

    @Override
    public MiasmaDemon copy() {
        return new MiasmaDemon(this);
    }
}

class MiasmaDemonEffect extends OneShotEffect {

    MiasmaDemonEffect() {
        super(Outcome.Benefit);
        staticText = "you may discard any number of cards. When you do, " +
                "up to that many target creatures each get -2/-2 until end of turn";
    }

    private MiasmaDemonEffect(final MiasmaDemonEffect effect) {
        super(effect);
    }

    @Override
    public MiasmaDemonEffect copy() {
        return new MiasmaDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        if (amount < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(-2, -2), false,
                "Up to that many target creatures each get -2/-2 until end of turn"
        );
        ability.addTarget(new TargetCreaturePermanent(0, amount));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
