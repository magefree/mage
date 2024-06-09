
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class Hateflayer extends CardImpl {

    public Hateflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Wither
        this.addAbility(WitherAbility.getInstance());
        
        // {2}{R}, {untap}: Hateflayer deals damage equal to its power to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new SourcePermanentPowerCount())
                .setText("{this} deals damage equal to its power to any target"), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        
    }

    private Hateflayer(final Hateflayer card) {
        super(card);
    }

    @Override
    public Hateflayer copy() {
        return new Hateflayer(this);
    }
}
