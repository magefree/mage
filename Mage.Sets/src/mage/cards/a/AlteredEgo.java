
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class AlteredEgo extends CardImpl {

    public AlteredEgo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{X}{2}{G}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Altered Ego can't be countered.
        this.addAbility(new CantBeCounteredAbility());

        // You may have Altered Ego enter the battlefield as a copy of any creature on the battlefield, except it enters with an additional X +1/+1 counters on it.
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, null);
        effect.setText("a copy of any creature on the battlefield");
        EntersBattlefieldAbility ability = new EntersBattlefieldAbility(effect, true);
        effect = new AlteredEgoAddCountersEffect(CounterType.P1P1.createInstance());
        effect.setText(", except it enters with an additional X +1/+1 counters on it");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public AlteredEgo(final AlteredEgo card) {
        super(card);
    }

    @Override
    public AlteredEgo copy() {
        return new AlteredEgo(this);
    }
}

class AlteredEgoAddCountersEffect extends EntersBattlefieldWithXCountersEffect {

    public AlteredEgoAddCountersEffect(Counter counter) {
        super(counter);
    }

    public AlteredEgoAddCountersEffect(EntersBattlefieldWithXCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            // except only takes place if something was copied
            if (permanent.isCopy()) {
                return super.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public EntersBattlefieldWithXCountersEffect copy() {
        return new AlteredEgoAddCountersEffect(this);
    }

}
