
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HeroOfLeinaTower extends CardImpl {

    public HeroOfLeinaTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> &mdash; Whenever you cast a spell that targets Hero of Leina Tower, you may pay {X}. If you do, put X +1/+1 counters on Hero of Leina Tower.
        this.addAbility(new HeroicAbility(new HeroOfLeinaTowerEffect()));

    }

    private HeroOfLeinaTower(final HeroOfLeinaTower card) {
        super(card);
    }

    @Override
    public HeroOfLeinaTower copy() {
        return new HeroOfLeinaTower(this);
    }
}

class HeroOfLeinaTowerEffect extends OneShotEffect {

    public HeroOfLeinaTowerEffect() {
        super(Outcome.BoostCreature);
        staticText = "you may pay {X}. If you do, put X +1/+1 counters on {this}";
    }

    public HeroOfLeinaTowerEffect(final HeroOfLeinaTowerEffect effect) {
        super(effect);
    }

    @Override
    public HeroOfLeinaTowerEffect copy() {
        return new HeroOfLeinaTowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl<>("{X}");
        if (you != null && you.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", source, game)) {
            int costX = you.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            cost.add(new GenericManaCost(costX));
            if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(costX), true).apply(game, source);
                }
            }
        }
        return false;
    }
}
