package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeadeyeBrawler extends CardImpl {

    public DeadeyeBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Ascend
        this.addAbility(new AscendAbility());

        // Whenever Deadeye Brawler deals combat damage to a player, if you have the city's blessing, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, false), CitysBlessingCondition.instance,
                "Whenever {this} deals combat damage to a player, if you have the city's blessing, draw a card.")
                .addHint(CitysBlessingHint.instance));

    }

    private DeadeyeBrawler(final DeadeyeBrawler card) {
        super(card);
    }

    @Override
    public DeadeyeBrawler copy() {
        return new DeadeyeBrawler(this);
    }
}
