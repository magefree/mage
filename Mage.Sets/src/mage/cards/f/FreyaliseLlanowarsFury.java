package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.ElfDruidToken;
import mage.target.TargetPermanent;

import java.util.UUID;
import mage.constants.SuperType;

/**
 *
 * @author LevelX2
 */
public final class FreyaliseLlanowarsFury extends CardImpl {

    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("green creature you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public FreyaliseLlanowarsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FREYALISE);

        this.setStartingLoyalty(3);

        // +2: Create a 1/1 green Elf Druid creature token with "{T}: Add {G}."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new ElfDruidToken()), 2));

        // -2: Destroy target artifact or enchantment.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        loyaltyAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(loyaltyAbility);

        // -6: Draw a card for each green creature you control.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filterGreen)), -6));

        // Freyalise, Llanowar's Fury can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private FreyaliseLlanowarsFury(final FreyaliseLlanowarsFury card) {
        super(card);
    }

    @Override
    public FreyaliseLlanowarsFury copy() {
        return new FreyaliseLlanowarsFury(this);
    }
}
