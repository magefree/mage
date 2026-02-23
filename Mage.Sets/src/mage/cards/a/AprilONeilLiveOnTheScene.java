package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PartnerVariantType;

/**
 *
 * @author muz
 */
public final class AprilONeilLiveOnTheScene extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent("a Mutant, Ninja, or Turtle you control");

    static {
        filter.add(Predicates.or(
            SubType.MUTANT.getPredicate(),
            SubType.NINJA.getPredicate(),
            SubType.TURTLE.getPredicate()
        ));
    }

    public AprilONeilLiveOnTheScene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a Mutant, Ninja, or Turtle you control enters, investigate.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new InvestigateEffect(), filter));

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private AprilONeilLiveOnTheScene(final AprilONeilLiveOnTheScene card) {
        super(card);
    }

    @Override
    public AprilONeilLiveOnTheScene copy() {
        return new AprilONeilLiveOnTheScene(this);
    }
}
