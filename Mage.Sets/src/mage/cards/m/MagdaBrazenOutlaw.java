package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author weirddan455
 */
public final class MagdaBrazenOutlaw extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.DWARF, "Dwarves");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.DWARF, "a Dwarf you control");
    private static final FilterCard filter3 = new FilterCard("an artifact or Dragon card");
    private static final FilterControlledPermanent filter4 = new FilterControlledPermanent(SubType.TREASURE, "Treasures");
    static {
        filter3.add(Predicates.or(CardType.ARTIFACT.getPredicate(), SubType.DRAGON.getPredicate()));
    }

    public MagdaBrazenOutlaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Other Dwarves you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)
        ));

        // Whenever a Dwarf you control becomes tapped, create a Treasure token.
        this.addAbility(new BecomesTappedTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false, filter2
        ));

        // Sacrifice five Treasures: Search your library for an artifact or Dragon card, put that card onto the battlefield, then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter3), false, true),
                new SacrificeTargetCost(new TargetControlledPermanent(5, filter4))
        ));
    }

    private MagdaBrazenOutlaw(final MagdaBrazenOutlaw card) {
        super(card);
    }

    @Override
    public MagdaBrazenOutlaw copy() {
        return new MagdaBrazenOutlaw(this);
    }
}
