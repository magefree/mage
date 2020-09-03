package mage.cards.z;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class ZulaportDuelist extends CardImpl {

    public ZulaportDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Zulaport Duelist enters the battlefield, up to one target creature gets -2/-0 until end of turn. Its controller mills two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ZulaportDuelistEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ZulaportDuelist(final ZulaportDuelist card) {
        super(card);
    }

    @Override
    public ZulaportDuelist copy() {
        return new ZulaportDuelist(this);
    }
}

class ZulaportDuelistEffect extends OneShotEffect {

    ZulaportDuelistEffect() {
        super(Outcome.Benefit);
        staticText = "up to one target creature gets -2/-0 until end of turn. Its controller mills two cards";
    }

    private ZulaportDuelistEffect(final ZulaportDuelistEffect effect) {
        super(effect);
    }

    @Override
    public ZulaportDuelistEffect copy() {
        return new ZulaportDuelistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostTargetEffect(-2, 0), source);
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        player.millCards(2, source, game);
        return true;
    }
}
