package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public final class DeepglowSkate extends CardImpl {

    public DeepglowSkate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Deepglow Skate enters the battlefield, double the number of each kind of counter on any number of target permanents.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeepglowSkateEffect(), false);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, new FilterPermanent(), false));
        this.addAbility(ability);
    }

    private DeepglowSkate(final DeepglowSkate card) {
        super(card);
    }

    @Override
    public DeepglowSkate copy() {
        return new DeepglowSkate(this);
    }
}

class DeepglowSkateEffect extends OneShotEffect {

    public DeepglowSkateEffect() {
        super(Outcome.Benefit);
        this.staticText = "double the number of each kind of counter on any number of target permanents";
    }

    private DeepglowSkateEffect(final DeepglowSkateEffect effect) {
        super(effect);
    }

    @Override
    public DeepglowSkateEffect copy() {
        return new DeepglowSkateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean didOne = false;

        for (Target target : source.getTargets()) {
            for (UUID targetID : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetID);
                if (permanent != null) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        Counter newCounter = new Counter(counter.getName(), counter.getCount());
                        permanent.addCounters(newCounter, source.getControllerId(), source, game);
                        didOne = true;
                    }
                }
            }
        }
        return didOne;
    }

}
