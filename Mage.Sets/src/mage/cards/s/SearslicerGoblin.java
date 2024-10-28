package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.GoblinToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SearslicerGoblin extends CardImpl {

    public SearslicerGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Raid -- At the beginning of your end step, if you attacked this turn, create a 1/1 red Goblin creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new GoblinToken()),
                false, RaidCondition.instance
        ).setAbilityWord(AbilityWord.RAID), new PlayerAttackedWatcher());
    }

    private SearslicerGoblin(final SearslicerGoblin card) {
        super(card);
    }

    @Override
    public SearslicerGoblin copy() {
        return new SearslicerGoblin(this);
    }
}
