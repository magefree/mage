
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
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
public final class NafsAsp extends CardImpl {

    public NafsAsp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Nafs Asp deals damage to a player, that player loses 1 life at the beginning of their next draw step unless they pay {1} before that draw step.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new UnlessPaysDelayedEffect(
            new ManaCostsImpl<>("{1}"), new LoseLifeTargetEffect(1), PhaseStep.DRAW, true,
            "that player loses 1 life at the beginning of their next draw step unless they pay {1} before that draw step."),
            false, true));
    }

    private NafsAsp(final NafsAsp card) {
        super(card);
    }

    @Override
    public NafsAsp copy() {
        return new NafsAsp(this);
    }
}
