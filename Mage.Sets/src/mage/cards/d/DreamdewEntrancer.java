package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamdewEntrancer extends CardImpl {

    public DreamdewEntrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Dreamdew Entrancer enters, tap up to one target creature and put three stun counters on it. If you control that creature, draw two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(3))
                .setText("and put three stun counters on it"));
        ability.addEffect(new DreamdewEntrancerEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private DreamdewEntrancer(final DreamdewEntrancer card) {
        super(card);
    }

    @Override
    public DreamdewEntrancer copy() {
        return new DreamdewEntrancer(this);
    }
}

class DreamdewEntrancerEffect extends OneShotEffect {

    DreamdewEntrancerEffect() {
        super(Outcome.Benefit);
        staticText = "if you control that creature, draw two cards";
    }

    private DreamdewEntrancerEffect(final DreamdewEntrancerEffect effect) {
        super(effect);
    }

    @Override
    public DreamdewEntrancerEffect copy() {
        return new DreamdewEntrancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && player.drawCards(2, source, game) > 0;
    }
}
