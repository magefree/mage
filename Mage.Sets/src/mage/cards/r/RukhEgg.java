
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RukhEggBirdToken;

/**
 *
 * @author anonymous
 */
public final class RukhEgg extends CardImpl {

    public RukhEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.BIRD, SubType.EGG);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Rukh Egg dies, create a 4/4 red Bird creature token with flying at the beginning of the next end step.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new CreateTokenEffect(new RukhEggBirdToken())));
        effect.setText("create a 4/4 red Bird creature token with flying at the beginning of the next end step");
        Ability ability = new DiesSourceTriggeredAbility(effect);
        this.addAbility(ability);
    }

    private RukhEgg(final RukhEgg card) {
        super(card);
    }

    @Override
    public RukhEgg copy() {
        return new RukhEgg(this);
    }
}
