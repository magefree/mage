
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MorphAbility;
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
public final class SnarlingUndorak extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Beast creature");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }

    public SnarlingUndorak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}: Target Beast creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl("{2}{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // Morph {1}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{1}{G}{G}")));
    }

    private SnarlingUndorak(final SnarlingUndorak card) {
        super(card);
    }

    @Override
    public SnarlingUndorak copy() {
        return new SnarlingUndorak(this);
    }
}
