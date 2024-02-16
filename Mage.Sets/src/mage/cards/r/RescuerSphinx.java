package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RescuerSphinx extends CardImpl {

    public RescuerSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Rescuer Sphinx enters the battlefield, you may return a nonland permanent you control to its owner's hand. If you do, Rescuer Sphinx enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new AsEntersBattlefieldAbility(new RescuerSphinxEffect()));
    }

    private RescuerSphinx(final RescuerSphinx card) {
        super(card);
    }

    @Override
    public RescuerSphinx copy() {
        return new RescuerSphinx(this);
    }
}

class RescuerSphinxEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("nonland permanent you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    RescuerSphinxEffect() {
        super(Outcome.Benefit);
        staticText = "you may return a nonland permanent you control to its owner's hand. " +
                "If you do, {this} enters the battlefield with a +1/+1 counter on it.";
    }

    private RescuerSphinxEffect(final RescuerSphinxEffect effect) {
        super(effect);
    }

    @Override
    public RescuerSphinxEffect copy() {
        return new RescuerSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Return a nonland permanent you control to your hand?", source, game)) {
            return false;
        }
        Target target = new TargetPermanent(0, 1, filter, true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !player.moveCards(permanent, Zone.HAND, source, game)) {
            return false;
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
    }
}