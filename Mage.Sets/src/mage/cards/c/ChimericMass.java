
package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ChimericMass extends CardImpl {

    public ChimericMass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}");

        // Chimeric Mass enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // {1}: Until end of turn, Chimeric Mass becomes a Construct artifact creature with "This creature's power and toughness are each equal to the number of charge counters on it."
        CountersSourceCount count = new CountersSourceCount(CounterType.CHARGE);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(0, 0, "Construct artifact creature with \"This creature's power and toughness are each equal to the number of charge counters on it.\"")
                        .withType(CardType.ARTIFACT)
                        .withSubType(SubType.CONSTRUCT)
                        .withAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetBasePowerToughnessSourceEffect(count, count, Duration.WhileOnBattlefield, SubLayer.SetPT_7b))),
                CardType.ARTIFACT, Duration.EndOfTurn).withDurationRuleAtStart(true), new GenericManaCost(1)));
    }

    private ChimericMass(final ChimericMass card) {
        super(card);
    }

    @Override
    public ChimericMass copy() {
        return new ChimericMass(this);
    }

}
