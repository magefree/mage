package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

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

        this.secondSideCardClazz = mage.cards.l.LoneWolfOfTheNatterknolls.class;

        // Whenever an opponent casts a spell during your turn, draw a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new SpellCastOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_A, true),
                MyTurnCondition.instance,
                "Whenever an opponent casts a spell during your turn, draw a card."
        ).addHint(MyTurnHint.instance));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hermit of the Natterknolls.
        this.addAbility(new TransformAbility());
        this.addAbility(new WerewolfFrontTriggeredAbility());
    }

    private HermitOfTheNatterknolls(final HermitOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public HermitOfTheNatterknolls copy() {
        return new HermitOfTheNatterknolls(this);
    }
}
