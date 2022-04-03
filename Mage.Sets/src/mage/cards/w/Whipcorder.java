
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Whipcorder extends CardImpl {

    public Whipcorder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {tap}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapTargetEffect(), new ManaCostsImpl("{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());        
        this.addAbility(ability);
        // Morph {W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{W}")));
    }

    private Whipcorder(final Whipcorder card) {
        super(card);
    }

    @Override
    public Whipcorder copy() {
        return new Whipcorder(this);
    }
}
