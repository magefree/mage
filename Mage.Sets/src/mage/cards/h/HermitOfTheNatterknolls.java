package mage.cards.h;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HermitOfTheNatterknolls extends TransformingDoubleFacedCard {

    public HermitOfTheNatterknolls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{2}{G}",
                "Lone Wolf of the Natterknolls",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );

        // Hermit of the Natterknolls
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever an opponent casts a spell during your turn, draw a card.
        this.getLeftHalfCard().addAbility(new SpellCastOpponentTriggeredAbility(
                new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_A, false
        ).withTriggerCondition(MyTurnCondition.instance));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Hermit of the Natterknolls.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Lone Wolf of the Natterknolls
        this.getRightHalfCard().setPT(3, 5);

        // Whenever an opponent cast a spell during your turn, draw two cards.
        this.getRightHalfCard().addAbility(new SpellCastOpponentTriggeredAbility(
                new DrawCardSourceControllerEffect(2), StaticFilters.FILTER_SPELL_A, false
        ).withTriggerCondition(MyTurnCondition.instance));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Lone Wolf of the Natterknolls.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private HermitOfTheNatterknolls(final HermitOfTheNatterknolls card) {
        super(card);
    }

    @Override
    public HermitOfTheNatterknolls copy() {
        return new HermitOfTheNatterknolls(this);
    }
}
