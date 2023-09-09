
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public final class DrainingWhelk extends CardImpl {

    public DrainingWhelk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Draining Whelk enters the battlefield, counter target spell. Put X +1/+1 counters on Draining Whelk, where X is that spell's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrainingWhelkEffect());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private DrainingWhelk(final DrainingWhelk card) {
        super(card);
    }

    @Override
    public DrainingWhelk copy() {
        return new DrainingWhelk(this);
    }
}

class DrainingWhelkEffect extends CounterTargetEffect {
    
    DrainingWhelkEffect() {
        super();
        staticText = "counter target spell. Put X +1/+1 counters on Draining Whelk, where X is that spell's mana value";
    }
    
    private DrainingWhelkEffect(final DrainingWhelkEffect effect) {
        super(effect);
    }
    
    @Override
    public DrainingWhelkEffect copy() {
        return new DrainingWhelkEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Spell targetSpell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (targetSpell != null) {
            int spellCMC = targetSpell.getManaValue();
            super.apply(game, source);
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(spellCMC)).apply(game, source);
            return true;
        }
        return false;
    }
}
