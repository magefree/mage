
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Styxo
 */
public final class Greedo extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Hunter or Rogue card");

    static {
        filterCard.add(Predicates.or(SubType.ROGUE.getPredicate(), SubType.HUNTER.getPredicate()));

    }

    public Greedo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RODIAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Creatures blocking or blocked by Greedo have first strike.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()), new BlockingAttackerIdPredicate(this.getId())));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, "Creatures blocking or blocked by {this} have first strike")));

        // When Greedo dies, you may search your library for Hunter or Rogue card, reveal it, and put it into your hand.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true), true));
    }

    private Greedo(final Greedo card) {
        super(card);
    }

    @Override
    public Greedo copy() {
        return new Greedo(this);
    }
}
