package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author noxx
 */
public final class DreadSlaver extends CardImpl {

    public DreadSlaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever a creature dealt damage by Dread Slaver this turn dies, return it to the battlefield under your control. That creature is a black Zombie in addition to its other colors and types.
        this.addAbility(new DealtDamageAndDiedTriggeredAbility(new DreadSlaverEffect(), false));
    }

    private DreadSlaver(final DreadSlaver card) {
        super(card);
    }

    @Override
    public DreadSlaver copy() {
        return new DreadSlaver(this);
    }
}

class DreadSlaverEffect extends OneShotEffect {

    public DreadSlaverEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield under your control. That creature is a black Zombie in addition to its other colors and types";
    }

    public DreadSlaverEffect(final DreadSlaverEffect effect) {
        super(effect);
    }

    @Override
    public DreadSlaverEffect copy() {
        return new DreadSlaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                ContinuousEffect effect = new AddCreatureTypeAdditionEffect(SubType.ZOMBIE, true);
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

}
