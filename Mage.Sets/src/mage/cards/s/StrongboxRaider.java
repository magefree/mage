package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrongboxRaider extends CardImpl {

    public StrongboxRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Raid -- When this creature enters, if you attacked this turn, exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, true, Duration.UntilEndOfYourNextTurn)
        ).withInterveningIf(RaidCondition.instance).setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private StrongboxRaider(final StrongboxRaider card) {
        super(card);
    }

    @Override
    public StrongboxRaider copy() {
        return new StrongboxRaider(this);
    }
}
