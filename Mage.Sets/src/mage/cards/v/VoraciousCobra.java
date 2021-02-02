
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class VoraciousCobra extends CardImpl {

    public VoraciousCobra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever Voracious Cobra deals combat damage to a creature, destroy that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new DestroyTargetEffect(), true, false, true));
    }

    private VoraciousCobra(final VoraciousCobra card) {
        super(card);
    }

    @Override
    public VoraciousCobra copy() {
        return new VoraciousCobra(this);
    }
}
