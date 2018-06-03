

package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class EiganjoCastle extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public EiganjoCastle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addSuperType(SuperType.LEGENDARY);
        this.addAbility(new WhiteManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, 2), new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }


    public EiganjoCastle(final EiganjoCastle card) {
        super(card);
    }

    @Override
    public EiganjoCastle copy() {
        return new EiganjoCastle(this);
    }

}
