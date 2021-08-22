package mage.cards.v;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author North
 */
public final class ViridianBetrayers extends CardImpl {

    private static final String rule = "{this} has infect as long as an opponent is poisoned.";

    public ViridianBetrayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Viridian Betrayers has infect as long as an opponent is poisoned.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(InfectAbility.getInstance()), PoisonedCondition.instance, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private ViridianBetrayers(final ViridianBetrayers card) {
        super(card);
    }

    @Override
    public ViridianBetrayers copy() {
        return new ViridianBetrayers(this);
    }
}

enum PoisonedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentUuid : opponents) {
            Player opponent = game.getPlayer(opponentUuid);
            if (opponent != null
                    && opponent.isInGame()
                    && opponent.getCounters().getCount(CounterType.POISON) > 0) {
                return true;
            }
        }
        return false;
    }
}
