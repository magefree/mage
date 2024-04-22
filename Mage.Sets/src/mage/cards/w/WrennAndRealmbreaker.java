package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.WrennAndRealmbreakerEmblem;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrennAndRealmbreaker extends CardImpl {

    public WrennAndRealmbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WRENN);
        this.setStartingLoyalty(4);

        // Lands you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_LANDS, false
        )));

        // +1: Up to one target land you control becomes a 3/3 Elemental creature with vigilance, hexproof, and haste until your next turn. It's still a land.
        Ability ability = new LoyaltyAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(
                        3, 3, "3/3 Elemental creature " +
                        "with vigilance, hexproof, and haste", SubType.ELEMENTAL
                ).withAbility(VigilanceAbility.getInstance())
                        .withAbility(HexproofAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.UntilYourNextTurn
        ), 1);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);

        // -2: Mill three cards. You may put a permanent card from among the milled cards into your hand.
        this.addAbility(new LoyaltyAbility(new MillThenPutInHandEffect(3, StaticFilters.FILTER_CARD_A_PERMANENT), -2));

        // -7: You get an emblem with "You may play lands and cast permanent spells from your graveyard."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new WrennAndRealmbreakerEmblem()), -7));
    }

    private WrennAndRealmbreaker(final WrennAndRealmbreaker card) {
        super(card);
    }

    @Override
    public WrennAndRealmbreaker copy() {
        return new WrennAndRealmbreaker(this);
    }
}
