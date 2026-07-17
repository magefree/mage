package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class BlackWidowDaringOperative extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public BlackWidowDaringOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(true));

        // When Black Widow enters, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // Whenever Black Widow deals combat damage to a player, each opponent loses life equal to the number of creature cards in your graveyard.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new LoseLifeOpponentsEffect(xValue)
                .setText("each opponent loses life equal to the number of creature cards in your graveyard")
        ).addHint(new ValueHint("Creature cards in your graveyard", xValue));
        this.addAbility(ability);
    }

    private BlackWidowDaringOperative(final BlackWidowDaringOperative card) {
        super(card);
    }

    @Override
    public BlackWidowDaringOperative copy() {
        return new BlackWidowDaringOperative(this);
    }
}
