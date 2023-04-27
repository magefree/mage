
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class SoulCharmer extends CardImpl {

    public SoulCharmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Soul Charmer deals combat damage to a creature, you gain 2 life unless they pay {2}.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new GainLifeEffect(2), new ManaCostsImpl<>("{2}")), false, true));
    }

    private SoulCharmer(final SoulCharmer card) {
        super(card);
    }

    @Override
    public SoulCharmer copy() {
        return new SoulCharmer(this);
    }
}
