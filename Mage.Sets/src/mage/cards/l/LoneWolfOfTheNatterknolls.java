
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
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
 *
 * @author LevelX2
 */
public final class LoneWolfOfTheNatterknolls extends CardImpl {

    public LoneWolfOfTheNatterknolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);

        this.nightCard = true;
        this.transformable = true;

        // Whenever an opponent cast a spell during your turn, draw two cards.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(2), new FilterSpell("a spell"), true),
                MyTurnCondition.instance,
                "Whenever an opponent casts a spell during your turn, draw two cards."
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lone Wolf of the Natterknolls.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public LoneWolfOfTheNatterknolls(final LoneWolfOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public LoneWolfOfTheNatterknolls copy() {
        return new LoneWolfOfTheNatterknolls(this);
    }
}
