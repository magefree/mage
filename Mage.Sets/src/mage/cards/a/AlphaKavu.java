
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AlphaKavu extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kavu creature");

    static {
        filter.add(SubType.KAVU.getPredicate());
    }

    public AlphaKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G}: Target Kavu creature gets -1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, 1, Duration.EndOfTurn),
            new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private AlphaKavu(final AlphaKavu card) {
        super(card);
    }

    @Override
    public AlphaKavu copy() {
        return new AlphaKavu(this);
    }
}
