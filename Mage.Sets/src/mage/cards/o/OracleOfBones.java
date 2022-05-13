package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OracleOfBones extends CardImpl {

    public OracleOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Tribute 2
        this.addAbility(new TributeAbility(2));
        // When Oracle of Bones enters the battlefield, if tribute wasn't paid, 
        // you may cast an instant or sorcery card from your hand without paying its mana cost.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CastFromHandForFreeEffect(
                        StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
                ), false),
                TributeNotPaidCondition.instance, "When {this} enters the battlefield, " +
                "if its tribute wasn't paid, you may cast an instant or " +
                "sorcery spell from your hand without paying its mana cost."
        ));
    }

    private OracleOfBones(final OracleOfBones card) {
        super(card);
    }

    @Override
    public OracleOfBones copy() {
        return new OracleOfBones(this);
    }
}
