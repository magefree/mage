package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlintRaker extends CardImpl {

    private static final DynamicValue xValue = new HighestManaValueCount(StaticFilters.FILTER_PERMANENT_ARTIFACTS);

    public GlintRaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Glint Raker gets +X/+0, where X is the highest mana value among artifacts you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // Whenever Glint Raker deals combat damage to a player, you may reveal that many cards from the top of your library. Put an artifact card revealed this way into your hand and the rest into your graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new RevealLibraryPickControllerEffect(
                        SavedDamageValue.MANY, 1,
                        StaticFilters.FILTER_CARD_ARTIFACT_AN,
                        PutCards.HAND, PutCards.GRAVEYARD, false
                ), true
        ));
    }

    private GlintRaker(final GlintRaker card) {
        super(card);
    }

    @Override
    public GlintRaker copy() {
        return new GlintRaker(this);
    }
}
