
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Plopman
 */
public final class BomberCorps extends CardImpl {

    public BomberCorps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Battalion - Whenever Bomber Corps and at least two other creatures attack, Bomber Corps deals 1 damage to any target.
        Ability ability = new BattalionAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private BomberCorps(final BomberCorps card) {
        super(card);
    }

    @Override
    public BomberCorps copy() {
        return new BomberCorps(this);
    }
}
