package mage.cards.i;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterAllEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Loki
 */
public final class IchorRats extends CardImpl {

    public IchorRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddPoisonCounterAllEffect(TargetController.EACH_PLAYER), false
        ));
    }

    private IchorRats(final IchorRats card) {
        super(card);
    }

    @Override
    public IchorRats copy() {
        return new IchorRats(this);
    }

}
