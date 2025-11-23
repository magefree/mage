package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.SoldierFirebendingToken;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CruelAdministrator extends CardImpl {

    public CruelAdministrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Raid -- This creature enters with a +1/+1 counter on it if you attacked this turn.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), RaidCondition.instance, ""
        ), "with a +1/+1 counter on it if you attacked this turn")
                .setAbilityWord(AbilityWord.RAID)
                .addHint(RaidHint.instance), new PlayerAttackedWatcher());

        // Whenever this creature attacks, create a 2/2 red Soldier creature token with firebending 1.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierFirebendingToken())));
    }

    private CruelAdministrator(final CruelAdministrator card) {
        super(card);
    }

    @Override
    public CruelAdministrator copy() {
        return new CruelAdministrator(this);
    }
}
