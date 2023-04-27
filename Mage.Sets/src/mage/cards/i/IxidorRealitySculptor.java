
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TurnFaceUpTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cg5
 */
public final class IxidorRealitySculptor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Face-down creatures");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("face-down creature");

    static {
        filter.add(FaceDownPredicate.instance);
        filterTarget.add(FaceDownPredicate.instance);
    }

    public IxidorRealitySculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Face-down creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // {2}{U}: Turn target face-down creature face up.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TurnFaceUpTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        this.addAbility(ability);
    }

    private IxidorRealitySculptor(final IxidorRealitySculptor card) {
        super(card);
    }

    @Override
    public IxidorRealitySculptor copy() {
        return new IxidorRealitySculptor(this);
    }
}
