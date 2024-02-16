
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class DeathCharmer extends CardImpl {

    public DeathCharmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.WORM);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Death Charmer deals combat damage to a creature, that creature's controller loses 2 life unless they pay {2}.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new LoseLifeTargetControllerEffect(2), new ManaCostsImpl<>("{2}")), false, true));
    }

    private DeathCharmer(final DeathCharmer card) {
        super(card);
    }

    @Override
    public DeathCharmer copy() {
        return new DeathCharmer(this);
    }
}
