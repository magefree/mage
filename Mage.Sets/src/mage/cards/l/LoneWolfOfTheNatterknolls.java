package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LoneWolfOfTheNatterknolls extends CardImpl {

    public LoneWolfOfTheNatterknolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.color.setGreen(true);

        this.nightCard = true;

        // Whenever an opponent cast a spell during your turn, draw two cards.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(2), StaticFilters.FILTER_SPELL_A, false),
                MyTurnCondition.instance,
                "Whenever an opponent casts a spell during your turn, draw two cards."
        ).addHint(MyTurnHint.instance));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lone Wolf of the Natterknolls.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private LoneWolfOfTheNatterknolls(final LoneWolfOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public LoneWolfOfTheNatterknolls copy() {
        return new LoneWolfOfTheNatterknolls(this);
    }
}
