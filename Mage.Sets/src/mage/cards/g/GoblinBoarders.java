package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinBoarders extends CardImpl {

    public GoblinBoarders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Raid -- This creature enters with a +1/+1 counter on it if you attacked this turn.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), RaidCondition.instance, ""
        ), "with a +1/+1 counter on it if you attacked this turn")
                .setAbilityWord(AbilityWord.RAID)
                .addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private GoblinBoarders(final GoblinBoarders card) {
        super(card);
    }

    @Override
    public GoblinBoarders copy() {
        return new GoblinBoarders(this);
    }
}
