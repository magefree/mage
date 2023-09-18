
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ScrybRanger extends CardImpl {

    private static final FilterControlledLandPermanent filterForest = new FilterControlledLandPermanent("a Forest");

    static {
        filterForest.add(SubType.FOREST.getPredicate());
    }

    public ScrybRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
        // Return a Forest you control to its owner's hand: Untap target creature. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filterForest)));
        ability.addTarget(new TargetCreaturePermanent(1));
        this.addAbility(ability);
    }

    private ScrybRanger(final ScrybRanger card) {
        super(card);
    }

    @Override
    public ScrybRanger copy() {
        return new ScrybRanger(this);
    }
}
