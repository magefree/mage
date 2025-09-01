package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnucklesTheEchidna extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledArtifactPermanent("you control thirty or more artifacts"),
            ComparisonType.MORE_THAN, 29
    );

    public KnucklesTheEchidna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ECHIDNA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more creatures you control deal combat damage to a player, create a Treasure token.
        this.addAbility(new OneOrMoreCombatDamagePlayerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), StaticFilters.FILTER_CONTROLLED_CREATURES
        ));

        // Treasure Hunter -- At the beginning of your upkeep, if you control thirty or more artifacts, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(condition).addHint(ArtifactYouControlHint.instance).withFlavorWord("Treasure Hunter"));
    }

    private KnucklesTheEchidna(final KnucklesTheEchidna card) {
        super(card);
    }

    @Override
    public KnucklesTheEchidna copy() {
        return new KnucklesTheEchidna(this);
    }
}
