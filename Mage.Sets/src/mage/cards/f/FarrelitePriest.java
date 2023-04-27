
package mage.cards.f;

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
public final class FarrelitePriest extends CardImpl {

    public FarrelitePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}: Add {W}. If this ability has been activated four or more times this turn, sacrifice Farrelite Priest at the beginning of the next end step.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new ManaCostsImpl<>("{1}"));
        ability.addEffect(new FarrelitePriestEffect());
        this.addAbility(ability);
    }

    private FarrelitePriest(final FarrelitePriest card) {
        super(card);
    }

    @Override
    public FarrelitePriest copy() {
        return new FarrelitePriest(this);
    }
}

class FarrelitePriestEffect extends OneShotEffect {

    public FarrelitePriestEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public FarrelitePriestEffect(final FarrelitePriestEffect effect) {
        super(effect);
    }

    @Override
    public FarrelitePriestEffect copy() {
        return new FarrelitePriestEffect(this);
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
