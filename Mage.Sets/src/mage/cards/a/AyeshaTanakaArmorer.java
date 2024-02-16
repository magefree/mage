package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.card.ManaValueLessThanOrEqualToSourcePowerPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyeshaTanakaArmorer extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard(
            "artifact cards with mana value less than or equal to {this}'s power"
    );
    private static final FilterPermanent filter2 = new FilterControlledArtifactPermanent();

    static {
        filter.add(ManaValueLessThanOrEqualToSourcePowerPredicate.instance);
        filter2.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter2, ComparisonType.MORE_THAN, 2, false
    );

    public AyeshaTanakaArmorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Ayesha Tanaka, Armorer attacks, look at the top four cards of your library. You may put any number of artifact cards with mana value less than or equal to Ayesha's power from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, Integer.MAX_VALUE, filter, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // Ayesha can't be blocked as long as defending player controls three or more artifacts.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), condition, "{this} can't be blocked " +
                "as long as defending player controls three or more artifacts"
        )));
    }

    private AyeshaTanakaArmorer(final AyeshaTanakaArmorer card) {
        super(card);
    }

    @Override
    public AyeshaTanakaArmorer copy() {
        return new AyeshaTanakaArmorer(this);
    }
}
