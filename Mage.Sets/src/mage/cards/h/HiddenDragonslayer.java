
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HiddenDragonslayer extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater an opponent controls");
    
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HiddenDragonslayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        
        // Megamorph {2}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{W}"), true));

        // When Hidden Dragonslayer is turned face up, destroy target creature with power 4 or greater an opponent controls.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new DestroyTargetEffect(), false, false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private HiddenDragonslayer(final HiddenDragonslayer card) {
        super(card);
    }

    @Override
    public HiddenDragonslayer copy() {
        return new HiddenDragonslayer(this);
    }
}
