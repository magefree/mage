package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BellowingSaddlebrute extends CardImpl {

    public BellowingSaddlebrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ORC, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Raid - When Bellowing Saddlebrute enters the battlefield, you lose 4 life unless you attacked with a creature this turn
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                        new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(4)),
                        new InvertCondition(RaidCondition.instance),
                        "When {this} enters the battlefield, you lose 4 life unless you attacked this turn.")
                        .setAbilityWord(AbilityWord.RAID)
                        .addHint(RaidHint.instance),
                new PlayerAttackedWatcher());
    }

    private BellowingSaddlebrute(final BellowingSaddlebrute card) {
        super(card);
    }

    @Override
    public BellowingSaddlebrute copy() {
        return new BellowingSaddlebrute(this);
    }
}
