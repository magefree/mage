
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class ArcSlogger extends CardImpl {

    public ArcSlogger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {R}, Exile the top ten cards of your library: Arc-Slogger deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl<>("{R}"));
        ability.addCost(new ExileFromTopOfLibraryCost(10));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ArcSlogger(final ArcSlogger card) {
        super(card);
    }

    @Override
    public ArcSlogger copy() {
        return new ArcSlogger(this);
    }
}
