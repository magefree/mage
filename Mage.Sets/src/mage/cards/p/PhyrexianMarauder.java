
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.combat.CantAttackBlockUnlessPaysSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class PhyrexianMarauder extends CardImpl {

    public PhyrexianMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{X}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Phyrexian Marauder enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // Phyrexian Marauder can't block.
        this.addAbility(new CantBlockAbility());

        // Phyrexian Marauder can't attack unless you pay {1} for each +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianMarauderCantAttackUnlessYouPayEffect()));
    }

    private PhyrexianMarauder(final PhyrexianMarauder card) {
        super(card);
    }

    @Override
    public PhyrexianMarauder copy() {
        return new PhyrexianMarauder(this);
    }
}

class PhyrexianMarauderCantAttackUnlessYouPayEffect extends CantAttackBlockUnlessPaysSourceEffect {

    PhyrexianMarauderCantAttackUnlessYouPayEffect() {
        super(new ManaCostsImpl<>("{0}"), RestrictType.ATTACK);
        staticText = "{this} can't attack unless you pay {1} for each +1/+1 counter on it";
    }

    PhyrexianMarauderCantAttackUnlessYouPayEffect(PhyrexianMarauderCantAttackUnlessYouPayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getSourceId());
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            int counter = sourceObject.getCounters(game).getCount(CounterType.P1P1);
            if (counter > 0) {
                return new ManaCostsImpl<>("{" + counter + '}');
            }
        }
        return null;
    }

    @Override
    public PhyrexianMarauderCantAttackUnlessYouPayEffect copy() {
        return new PhyrexianMarauderCantAttackUnlessYouPayEffect(this);
    }

}
