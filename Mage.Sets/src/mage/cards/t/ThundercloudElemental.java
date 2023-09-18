
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class ThundercloudElemental extends CardImpl {

    private static final FilterCreaturePermanent toughnessFilter = new FilterCreaturePermanent("creatures with toughness 2 or less");
    private static final FilterCreaturePermanent flyingFilter = new FilterCreaturePermanent("All other creatures");

    static {
        toughnessFilter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
        flyingFilter.add(AnotherPredicate.instance);
    }

    public ThundercloudElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{U}: Tap all creatures with toughness 2 or less.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(toughnessFilter), new ManaCostsImpl<>("{3}{U}")));

        // {3}{U}: All other creatures lose flying until end of turn.
        Effect effect = new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, flyingFilter);
        effect.setText("All other creatures lose flying until end of turn");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{3}{U}")));

    }

    private ThundercloudElemental(final ThundercloudElemental card) {
        super(card);
    }

    @Override
    public ThundercloudElemental copy() {
        return new ThundercloudElemental(this);
    }
}
