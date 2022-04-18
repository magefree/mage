
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.UnlessPaysDelayedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;

/**
 *
 * @author LoneFox
 */
public final class GlassAsp extends CardImpl {

    public GlassAsp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Glass Asp deals combat damage to a player, that player loses 2 life at the beginning of their next draw step unless they pay {2} before that step.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new UnlessPaysDelayedEffect(
                new ManaCostsImpl("{2}"), new LoseLifeTargetEffect(2), PhaseStep.DRAW, true,
                "that player loses 2 life at the beginning of their next draw step unless they pay {2} before that step."),
                false, true));
    }

    private GlassAsp(final GlassAsp card) {
        super(card);
    }

    @Override
    public GlassAsp copy() {
        return new GlassAsp(this);
    }
}
