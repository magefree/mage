package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NightkinAmbusher extends CardImpl {

    public NightkinAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // When Nightkin Ambusher enters the battlefield, target player gets four rad counters.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.RAD.createInstance(4)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Nightkin Ambusher can't be blocked as long as defending player has a rad counter.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), NightkinAmbusherCondition.instance
        ).setText("{this} can't be blocked as long as defending player has a rad counter")));
    }

    private NightkinAmbusher(final NightkinAmbusher card) {
        super(card);
    }

    @Override
    public NightkinAmbusher copy() {
        return new NightkinAmbusher(this);
    }
}

enum NightkinAmbusherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(game.getCombat().getDefendingPlayerId(source.getSourceId(), game));
        return defendingPlayer != null && defendingPlayer.getCountersCount(CounterType.RAD) >= 1;
    }
}