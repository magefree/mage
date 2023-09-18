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
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author LevelX2
 */
public final class MinamoSightbender extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature with power X or less");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public MinamoSightbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {X}, {T}: Target creature with power X or less can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{X}"));
        Target target = new TargetPermanent(filter);
        ability.setTargetAdjuster(MinamoSightbenderAdjuster.instance);
        ability.addTarget(target);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private MinamoSightbender(final MinamoSightbender card) {
        super(card);
    }

    @Override
    public MinamoSightbender copy() {
        return new MinamoSightbender(this);
    }
}

enum MinamoSightbenderAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent permanentFilter = new FilterCreaturePermanent("creature with power " + xValue + " or less");
        permanentFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.getTargets().add(new TargetPermanent(permanentFilter));
    }
}
