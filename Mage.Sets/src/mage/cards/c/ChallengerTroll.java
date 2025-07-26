package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChallengerTroll extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }


    public ChallengerTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Each creature you control with power 4 or greater can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByMoreThanOneAllEffect(filter)));
    }

    private ChallengerTroll(final ChallengerTroll card) {
        super(card);
    }

    @Override
    public ChallengerTroll copy() {
        return new ChallengerTroll(this);
    }
}
