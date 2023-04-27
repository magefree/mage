package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.DamageDoneWatcher;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class WaveOfRats extends CardImpl {

    public WaveOfRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.addSubType(SubType.RAT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Wave of Rats dies, if it dealt combat damage to a player this turn, return it to the battlefield under its owner’s control.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new ReturnToBattlefieldUnderOwnerControlSourceEffect()),
                WaveOfRatsDealtDamageToPlayerCondition.instance,
                "When Wave of Rats dies, if it dealt combat damage to a player this turn, return it to the battlefield under its owner's control.")
        );

        // Blitz {4}{B} (If you cast this spell for its blitz cost, it gains haste and “When this creature dies, draw a card.” Sacrifice it at the beginning of the next end step.)
        this.addAbility(new BlitzAbility(this, "{4}{B}"));
    }

    private WaveOfRats(final WaveOfRats card) {
        super(card);
    }

    @Override
    public WaveOfRats copy() {
        return new WaveOfRats(this);
    }
}

enum WaveOfRatsDealtDamageToPlayerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        DamageDoneWatcher watcher = game.getState().getWatcher(DamageDoneWatcher.class);
        Permanent waveOfRats = game.getPermanent(source.getSourceId());
        if (watcher == null || waveOfRats == null) {
            return false;
        }
        if (watcher.damageDoneBy(waveOfRats.getId(), waveOfRats.getZoneChangeCounter(game), game) < 1) {
            return false;
        }
        return watcher.damagedAPlayer(waveOfRats.getId(), waveOfRats.getZoneChangeCounter(game), game);
    }
}