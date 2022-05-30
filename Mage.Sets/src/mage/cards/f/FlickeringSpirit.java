package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlickeringSpirit extends CardImpl {

    public FlickeringSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Exile Flickering Spirit, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleActivatedAbility(new FlickeringSpiritEffect(), new ManaCostsImpl<>("{3}{W}")));
    }

    private FlickeringSpirit(final FlickeringSpirit card) {
        super(card);
    }

    @Override
    public FlickeringSpirit copy() {
        return new FlickeringSpirit(this);
    }
}

class FlickeringSpiritEffect extends OneShotEffect {

    FlickeringSpiritEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield under its owner's control";
    }

    private FlickeringSpiritEffect(final FlickeringSpiritEffect effect) {
        super(effect);
    }

    @Override
    public FlickeringSpiritEffect copy() {
        return new FlickeringSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null
                || player == null) {
            return false;
        }
        if (player.moveCards(permanent, Zone.EXILED, source, game)) {
            Card card = game.getExile().getCard(source.getSourceId(), game);
            if (card != null) {
                return player.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        return false;
    }
}
