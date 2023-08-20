package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jbureau88
 */
public final class OptimusPrimeHero extends CardImpl {

    public OptimusPrimeHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(8);
        this.secondSideCardClazz = mage.cards.o.OptimusPrimeAutobotLeader.class;

        // More Than Meets the Eye {2}{U}{R}{W}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{U}{R}{W}"));

        // At the beginning of each end step, bolster 1.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new BolsterEffect(1), TargetController.ANY, false));

        // When Optimus Prime dies, return it to the battlefield converted under its owner’s control.
        this.addAbility(new DiesSourceTriggeredAbility(new OptimusPrimeHeroEffect()));
    }

    private OptimusPrimeHero(final OptimusPrimeHero card) {
        super(card);
    }

    @Override
    public OptimusPrimeHero copy() {
        return new OptimusPrimeHero(this);
    }
}

class OptimusPrimeHeroEffect extends OneShotEffect {

    OptimusPrimeHeroEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield converted under its owner's control.";
    }

    private OptimusPrimeHeroEffect(final OptimusPrimeHeroEffect effect) {
        super(effect);
    }

    @Override
    public OptimusPrimeHeroEffect copy() {
        return new OptimusPrimeHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (card == null || controller == null) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        return controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
