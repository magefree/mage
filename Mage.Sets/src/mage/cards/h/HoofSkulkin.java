
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public final class HoofSkulkin extends CardImpl {

    private static final FilterCreaturePermanent filterGreenCreature = new FilterCreaturePermanent("green creature");

    static {
        filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public HoofSkulkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SCARECROW);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(3));
        ability.addTarget(new TargetCreaturePermanent(filterGreenCreature));
        this.addAbility(ability);
    }

    private HoofSkulkin(final HoofSkulkin card) {
        super(card);
    }

    @Override
    public HoofSkulkin copy() {
        return new HoofSkulkin(this);
    }
}
