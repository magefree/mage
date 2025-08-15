
package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class HaruHiddenTalent extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY, "another Ally you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public HaruHiddenTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Ally you control enters, earthbend 1.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(new EarthbendTargetEffect(1), filter);
        ability.addTarget(new TargetControlledLandPermanent());
        this.addAbility(ability);
    }

    private HaruHiddenTalent(final HaruHiddenTalent card) {
        super(card);
    }

    @Override
    public HaruHiddenTalent copy() {
        return new HaruHiddenTalent(this);
    }
}
