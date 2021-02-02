
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.RenownAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Wehk
 */
public final class AcolyteOfTheInferno extends CardImpl {

    public AcolyteOfTheInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Renown 1
        this.addAbility(new RenownAbility(1));
        
        // Whenever Acolyte of the Inferno becomes blocked by a creature, it deals 2 damage to that creature
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DamageTargetEffect(2, true, "that creature", "it"), false));
    }

    private AcolyteOfTheInferno(final AcolyteOfTheInferno card) {
        super(card);
    }

    @Override
    public AcolyteOfTheInferno copy() {
        return new AcolyteOfTheInferno(this);
    }
}
