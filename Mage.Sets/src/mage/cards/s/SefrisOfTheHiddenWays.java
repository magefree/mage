package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CompletedDungeonTriggeredAbility;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SefrisOfTheHiddenWays extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("one or more creature cards");

    public SefrisOfTheHiddenWays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever one or more creature cards are put into your graveyard from anywhere, venture into the dungeon. This ability triggers only once each turn.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new VentureIntoTheDungeonEffect(), false, filter, TargetController.YOU
        ).setTriggersOnceEachTurn(true));

        // Create Undead — Whenever you complete a dungeon, return target creature card from your graveyard to the battlefield.
        Ability ability = new CompletedDungeonTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability.withFlavorWord("Create Undead"));
    }

    private SefrisOfTheHiddenWays(final SefrisOfTheHiddenWays card) {
        super(card);
    }

    @Override
    public SefrisOfTheHiddenWays copy() {
        return new SefrisOfTheHiddenWays(this);
    }
}
