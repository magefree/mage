package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorchedGeyser extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final YouControlPermanentCondition condition =
            new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public ScorchedGeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.ISLAND);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {U} or {R}.)
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // This land enters tapped unless you control two or more basic lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));
    }

    private ScorchedGeyser(final ScorchedGeyser card) {
        super(card);
    }

    @Override
    public ScorchedGeyser copy() {
        return new ScorchedGeyser(this);
    }
}
