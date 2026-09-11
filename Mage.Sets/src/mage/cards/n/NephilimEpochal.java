package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.MonocoloredPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class NephilimEpochal extends CardImpl {

    private static final FilterCard filterCard = new FilterCard(SubType.NEPHILIM, "a Nephilim card");
    private static final FilterCreaturePermanent filterNephilim = new FilterCreaturePermanent(SubType.NEPHILIM, "Nephilim you control");
    private static final FilterControlledPermanent countFilter = new FilterControlledPermanent(SubType.NEPHILIM, "each other Nephilim you control");
    private static final FilterCreaturePermanent filterMonocolored = new FilterCreaturePermanent("monocolored creatures");

    static {
        filterNephilim.add(TargetController.YOU.getControllerPredicate());
        countFilter.add(AnotherPredicate.instance);
        filterMonocolored.add(MonocoloredPredicate.instance);
    }

    public NephilimEpochal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U/R}{R/G}{G/W}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.NEPHILIM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Nephilim Epochal enters, search your library for a Nephilim card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true)
        ));

        // Nephilim you control get +1/+1 for each other Nephilim you control and can't be blocked by monocolored creatures.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
            new PermanentsOnBattlefieldCount(countFilter),
            new PermanentsOnBattlefieldCount(countFilter),
            Duration.WhileOnBattlefield,
            filterNephilim, false
        ).setText("nephilim you control get +1/+1 for each other Nephilim you control"));
        ability.addEffect(new CantBeBlockedByCreaturesAllEffect(
            filterNephilim,
            filterMonocolored,
            Duration.WhileOnBattlefield
        ).setText("and can't be blocked by monocolored creatures"));
        this.addAbility(ability);
    }

    private NephilimEpochal(final NephilimEpochal card) {
        super(card);
    }

    @Override
    public NephilimEpochal copy() {
        return new NephilimEpochal(this);
    }
}
