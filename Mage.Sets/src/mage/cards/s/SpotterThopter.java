package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpotterThopter extends CardImpl {

    public SpotterThopter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.THOPTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Prototype {3}{U} -- 2/3
        this.addAbility(new PrototypeAbility(this, "{3}{U}", 2, 3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spotter Thopter enters the battlefield, scry X, where X is its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpotterThopterEffect()));
    }

    private SpotterThopter(final SpotterThopter card) {
        super(card);
    }

    @Override
    public SpotterThopter copy() {
        return new SpotterThopter(this);
    }
}

class SpotterThopterEffect extends OneShotEffect {

    SpotterThopterEffect() {
        super(Outcome.Benefit);
        staticText = "scry X, where X is its power";
    }

    private SpotterThopterEffect(final SpotterThopterEffect effect) {
        super(effect);
    }

    @Override
    public SpotterThopterEffect copy() {
        return new SpotterThopterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (player == null || permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        return power > 0 && player.scry(power, source, game);
    }
}
