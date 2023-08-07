package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author wetterlicht & L_J
 */
public final class CragSaurian extends CardImpl {

    public CragSaurian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a source deals damage to Crag Saurian, that source's controller gains control of Crag Saurian.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(new CragSaurianEffect()));
    }

    private CragSaurian(final CragSaurian card) {
        super(card);
    }

    @Override
    public CragSaurian copy() {
        return new CragSaurian(this);
    }
}

class CragSaurianEffect extends OneShotEffect {

    public CragSaurianEffect() {
        super(Outcome.GainControl);
        this.staticText = "that source's controller gains control of {this}";
    }

    private CragSaurianEffect(CragSaurianEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player newController = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (newController != null && controller != null && !controller.equals(newController)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public Effect copy() {
        return new CragSaurianEffect(this);
    }
}
