package mage.cards.s;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SivitriDragonMaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card");
    private static final FilterCreaturePermanent filterNonDragon = new FilterCreaturePermanent("non-Dragon creatures");

    static {
        filter.add(SubType.DRAGON.getPredicate());
        filterNonDragon.add(Predicates.not(SubType.DRAGON.getPredicate()));
    }

    public SivitriDragonMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIVITRI);

        this.setStartingLoyalty(4);

        // +1: Until your next turn, creatures can’t attack you or planeswalkers you
        // control unless their controller pays 2 life for each of those creatures.
        ContinuousEffect effect = new CantAttackYouUnlessPayAllEffect(
            Duration.UntilYourNextTurn,
            new PayLifeCost(2),
            CantAttackYouUnlessPayAllEffect.Scope.YOU_AND_CONTROLLED_PLANESWALKERS
        );
        this.addAbility(new LoyaltyAbility(effect, 1));

        // -3: Search your library for a Dragon card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true
        ), -3));

        // -7: Destroy all non-Dragon creatures.
        this.addAbility(new LoyaltyAbility(new DestroyAllEffect(filterNonDragon), -7));

        // Sivitri, Dragon Master can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private SivitriDragonMaster(final SivitriDragonMaster card) {
        super(card);
    }

    @Override
    public SivitriDragonMaster copy() {
        return new SivitriDragonMaster(this);
    }
}
