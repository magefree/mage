package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FormidableSpeaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FormidableSpeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When this creature enters, you may discard a card. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), true
                ), new DiscardCardCost())
        ));

        // {1}, {T}: Untap another target permanent.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FormidableSpeaker(final FormidableSpeaker card) {
        super(card);
    }

    @Override
    public FormidableSpeaker copy() {
        return new FormidableSpeaker(this);
    }
}
