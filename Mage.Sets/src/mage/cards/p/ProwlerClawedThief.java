package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProwlerClawedThief extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.VILLAIN, "another Villain you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ProwlerClawedThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another Villain you control enters, Prowler connives.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new ConniveSourceEffect("{this}"), filter));
    }

    private ProwlerClawedThief(final ProwlerClawedThief card) {
        super(card);
    }

    @Override
    public ProwlerClawedThief copy() {
        return new ProwlerClawedThief(this);
    }
}
