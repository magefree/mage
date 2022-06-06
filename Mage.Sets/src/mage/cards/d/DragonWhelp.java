
package mage.cards.d;

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
 * @author North
 */
public final class DragonWhelp extends CardImpl {

    public DragonWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Dragon Whelp gets +1/+0 until end of turn. If this ability has been activated four or more times this turn, sacrifice Dragon Whelp at the beginning of the next end step.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{R}"));
        ability.addEffect(new DragonWhelpEffect());
        this.addAbility(ability);
    }

    private DragonWhelp(final DragonWhelp card) {
        super(card);
    }

    @Override
    public DragonWhelp copy() {
        return new DragonWhelp(this);
    }
}

class DragonWhelpEffect extends OneShotEffect {

    public DragonWhelpEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public DragonWhelpEffect(final DragonWhelpEffect effect) {
        super(effect);
    }

    @Override
    public DragonWhelpEffect copy() {
        return new DragonWhelpEffect(this);
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
