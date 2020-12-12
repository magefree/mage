package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Brightmare extends CardImpl {

    public Brightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Brightmare enters the battlefield, tap up to one target creature. You gain life equal to that creature's power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BrightmareEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private Brightmare(final Brightmare card) {
        super(card);
    }

    @Override
    public Brightmare copy() {
        return new Brightmare(this);
    }
}

class BrightmareEffect extends OneShotEffect {

    BrightmareEffect() {
        super(Outcome.Benefit);
        staticText = "tap up to one target creature. You gain life equal to that creature's power";
    }

    private BrightmareEffect(final BrightmareEffect effect) {
        super(effect);
    }

    @Override
    public BrightmareEffect copy() {
        return new BrightmareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.tap(source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(permanent.getPower().getValue(), game, source);
        }
        return true;
    }
}