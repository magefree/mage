package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OracleOfBones extends CardImpl {

    private static final FilterCard filter = new FilterInstantOrSorceryCard("an instant or sorcery spell");

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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CastFromHandForFreeEffect(filter))
                .withInterveningIf(TributeNotPaidCondition.instance));
    }

    private OracleOfBones(final OracleOfBones card) {
        super(card);
    }

    @Override
    public OracleOfBones copy() {
        return new OracleOfBones(this);
    }
}
