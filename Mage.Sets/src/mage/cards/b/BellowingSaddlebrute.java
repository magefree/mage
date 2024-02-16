package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
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
 * @author awjackson
 */
public final class BellowingSaddlebrute extends CardImpl {

    public BellowingSaddlebrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ORC, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Raid - When Bellowing Saddlebrute enters the battlefield, you lose 4 life unless you attacked this turn
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new LoseLifeSourceControllerEffect(4),
                new InvertCondition(RaidCondition.instance),
                "you lose 4 life unless you attacked this turn"
        ));
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private BellowingSaddlebrute(final BellowingSaddlebrute card) {
        super(card);
    }

    @Override
    public BellowingSaddlebrute copy() {
        return new BellowingSaddlebrute(this);
    }
}
