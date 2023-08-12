
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivationInfo;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author MarcoMarin
 */
public final class InitiatesOfTheEbonHand extends CardImpl {

    public InitiatesOfTheEbonHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B}. If this ability has been activated four or more times this turn, sacrifice Initiates of the Ebon Hand at the beginning of the next end step.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new ManaCostsImpl<>("{1}"));
        ability.addEffect(new InitiatesOfTheEbonHandEffect());
        this.addAbility(ability);
    }

    private InitiatesOfTheEbonHand(final InitiatesOfTheEbonHand card) {
        super(card);
    }

    @Override
    public InitiatesOfTheEbonHand copy() {
        return new InitiatesOfTheEbonHand(this);
    }
}

class InitiatesOfTheEbonHandEffect extends OneShotEffect {

    public InitiatesOfTheEbonHandEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public InitiatesOfTheEbonHandEffect(final InitiatesOfTheEbonHandEffect effect) {
        super(effect);
    }

    @Override
    public InitiatesOfTheEbonHandEffect copy() {
        return new InitiatesOfTheEbonHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ActivationInfo activationInfo = ActivationInfo.getInstance(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        activationInfo.addActivation(game);
        if (activationInfo.getActivationCounter() == 4) {
            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect());
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;
    }

}
