
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class KelsinkoRanger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public KelsinkoRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}: Target green creature gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{W}")
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private KelsinkoRanger(final KelsinkoRanger card) {
        super(card);
    }

    @Override
    public KelsinkoRanger copy() {
        return new KelsinkoRanger(this);
    }
}
