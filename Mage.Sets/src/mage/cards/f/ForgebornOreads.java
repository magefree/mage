
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class ForgebornOreads extends CardImpl {

    public ForgebornOreads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.NYMPH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Constellation - Whenever Forgeborn Oreads or another enchantment enters the battlefield under your control, Forgeborn Oreads deals 1 damage to any target.
        Ability ability = new ConstellationAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private ForgebornOreads(final ForgebornOreads card) {
        super(card);
    }

    @Override
    public ForgebornOreads copy() {
        return new ForgebornOreads(this);
    }
}
