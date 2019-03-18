
package mage.cards.h;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

/**
 * @author LevelX2
 */
public final class HermitOfTheNatterknolls extends CardImpl {

    public HermitOfTheNatterknolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.l.LoneWolfOfTheNatterknolls.class;

        // Whenever an opponent casts a spell during your turn, draw a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1), new FilterSpell("a spell"), true),
                MyTurnCondition.instance,
                "Whenever an opponent casts a spell during your turn, draw a card."
        ));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hermit of the Natterknolls.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public HermitOfTheNatterknolls(final HermitOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public HermitOfTheNatterknolls copy() {
        return new HermitOfTheNatterknolls(this);
    }
}
