package mage.cards.d;

import mage.MageInt;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DryadSophisticate extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("nonbasic land");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public DryadSophisticate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Nonbasic landwalk
        this.addAbility(new LandwalkAbility(filter));
    }

    private DryadSophisticate(final DryadSophisticate card) {
        super(card);
    }

    @Override
    public DryadSophisticate copy() {
        return new DryadSophisticate(this);
    }
}
