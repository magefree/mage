
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class MinamoSightbender extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature with power X or less");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public MinamoSightbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {X}, {T}: Target creature with power X or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new ManaCostsImpl("{X}"));
        Target target = new TargetPermanent(filter);
        ability.setTargetAdjustment(TargetAdjustment.X_POWER_LEQ);
        ability.addTarget(target);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public MinamoSightbender(final MinamoSightbender card) {
        super(card);
    }

    @Override
    public MinamoSightbender copy() {
        return new MinamoSightbender(this);
    }
}
