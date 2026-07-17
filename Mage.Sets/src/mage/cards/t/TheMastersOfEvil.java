package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class TheMastersOfEvil extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VILLAIN, "Villains");
    private static final FilterCard filter2 = new FilterCard(SubType.PLAN);

    public TheMastersOfEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Other Villains you control get +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            2, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {1}{B}, Discard this card: Search your library for a Plan card, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(
            Zone.HAND,
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter2), true),
            new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    private TheMastersOfEvil(final TheMastersOfEvil card) {
        super(card);
    }

    @Override
    public TheMastersOfEvil copy() {
        return new TheMastersOfEvil(this);
    }
}
