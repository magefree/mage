package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author NinthWorld
 */
public final class Stalker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 2 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public Stalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Stalker becomes blocked by a creature with power 2 or greater, return Stalker to its owner's hand.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new ReturnToHandSourceEffect(), filter, false));
    }

    public Stalker(final Stalker card) {
        super(card);
    }

    @Override
    public Stalker copy() {
        return new Stalker(this);
    }
}
