package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author TheElk801
 */
public final class PyricSalamander extends CardImpl {

    public PyricSalamander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {R}: Pyric Salamander gets +1/+0 until end of turn. Sacrifice Pyric Salamander at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("Sacrifice {this} at the beginning of the next end step"));
        this.addAbility(ability);
    }

    private PyricSalamander(final PyricSalamander card) {
        super(card);
    }

    @Override
    public PyricSalamander copy() {
        return new PyricSalamander(this);
    }
}
