package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SculptorOfWinter extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("snow land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    public SculptorOfWinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Untap target snow land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SculptorOfWinter(final SculptorOfWinter card) {
        super(card);
    }

    @Override
    public SculptorOfWinter copy() {
        return new SculptorOfWinter(this);
    }
}
