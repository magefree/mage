package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author tiera3 - based on PyricSalamander, AkroanLineBreaker
 */
public final class GoblinSkiPatrol extends CardImpl {

    public GoblinSkiPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Goblin Ski Patrol gets +2/+0 and gains flying. Its controller sacrifices it at the beginning of the next end step. Activate only once and only if you control a snow Mountain.
        Effect effect = new BoostSourceEffect(2, 0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}{R}"));
        effect = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying.");
        ability.addEffect(effect);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("Its controller sacrifices it at the beginning of the next end step."));
        this.addAbility(ability);
    }

    private GoblinSkiPatrol(final GoblinSkiPatrol card) {
        super(card);
    }

    @Override
    public GoblinSkiPatrol copy() {
        return new GoblinSkiPatrol(this);
    }
}
