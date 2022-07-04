
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivationInfo;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author North & L_J
 */
public final class NalathniDragon extends CardImpl {

    public NalathniDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // {R}: Nalathni Dragon gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Nalathni Dragon at the beginning of the next end step.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"));
        ability.addEffect(new NalathniDragonEffect());
        this.addAbility(ability);
    }

    private NalathniDragon(final NalathniDragon card) {
        super(card);
    }

    @Override
    public NalathniDragon copy() {
        return new NalathniDragon(this);
    }
}

class NalathniDragonEffect extends OneShotEffect {

    public NalathniDragonEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public NalathniDragonEffect(final NalathniDragonEffect effect) {
        super(effect);
    }

    @Override
    public NalathniDragonEffect copy() {
        return new NalathniDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ActivationInfo activationInfo = ActivationInfo.getInstance(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        activationInfo.addActivation(game);
        if (activationInfo.getActivationCounter() >= 4) {
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

}
