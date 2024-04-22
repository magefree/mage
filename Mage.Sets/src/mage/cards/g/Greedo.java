package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class Greedo extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature blocking or blocked by {this}");
    private static final FilterCard filterCard = new FilterCard("Hunter or Rogue card");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
        filterCard.add(Predicates.or(
                SubType.ROGUE.getPredicate(),
                SubType.HUNTER.getPredicate()
        ));

    }

    public Greedo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RODIAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creatures blocking or blocked by Greedo have first strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter,
                "Creatures blocking or blocked by {this} have first strike"
        )));

        // When Greedo dies, you may search your library for Hunter or Rogue card, reveal it, and put it into your hand.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filterCard), true
        ), true));
    }

    private Greedo(final Greedo card) {
        super(card);
    }

    @Override
    public Greedo copy() {
        return new Greedo(this);
    }
}
