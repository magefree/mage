
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author jeffwadsworth
 */
public final class CragganwickCremator extends CardImpl {

    public CragganwickCremator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Cragganwick Cremator enters the battlefield, discard a card at random. If you discard a creature card this way, Cragganwick Cremator deals damage equal to that card's power to target player or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CragganwickCrematorEffect(), false);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

    }

    private CragganwickCremator(final CragganwickCremator card) {
        super(card);
    }

    @Override
    public CragganwickCremator copy() {
        return new CragganwickCremator(this);
    }
}

class CragganwickCrematorEffect extends OneShotEffect {

    public CragganwickCrematorEffect() {
        super(Outcome.Neutral);
        this.staticText = "discard a card at random. If you discard a creature card this way, {this} deals damage equal to that card's power to target player or planeswalker";
    }

    public CragganwickCrematorEffect(final CragganwickCrematorEffect effect) {
        super(effect);
    }

    @Override
    public CragganwickCrematorEffect copy() {
        return new CragganwickCrematorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card discardedCard = controller.discardOne(true, false, source, game);
            if (discardedCard != null
                    && discardedCard.isCreature(game)) {
                int damage = discardedCard.getPower().getValue();
                return new DamageTargetEffect(damage).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
