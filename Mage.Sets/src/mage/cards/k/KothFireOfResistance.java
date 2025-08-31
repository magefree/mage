package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicCard;
import mage.filter.common.FilterControlledPermanent;
import mage.game.command.emblems.KothFireOfResistanceEmblem;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class KothFireOfResistance extends CardImpl {

    private static final FilterCard filter = new FilterBasicCard(SubType.MOUNTAIN);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.MOUNTAIN, "Mountains you control")
    );
    private static final Hint hint = new ValueHint(xValue.getMessage(), xValue);

    public KothFireOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOTH);
        this.setStartingLoyalty(4);

        // +2: Search your library for a basic Mountain card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), 2));

        // −3: Koth, Fire of Resistance deals damage to target creature equal to the number of Mountains you control.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(xValue)
                .setText("{this} deals damage to target creature equal to the number of Mountains you control"), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.addHint(hint));

        // −7: You get an emblem with "Whenever a Mountain you control enters, this emblem deals 4 damage to any target."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KothFireOfResistanceEmblem()), -7));
    }

    private KothFireOfResistance(final KothFireOfResistance card) {
        super(card);
    }

    @Override
    public KothFireOfResistance copy() {
        return new KothFireOfResistance(this);
    }
}
