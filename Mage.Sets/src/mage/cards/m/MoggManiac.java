
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class MoggManiac extends CardImpl {

    public MoggManiac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Mogg Maniac is dealt damage, it deals that much damage to target opponent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new MoggManiacDealDamageEffect(), false, false, true);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    public MoggManiac(final MoggManiac card) {
        super(card);
    }

    @Override
    public MoggManiac copy() {
        return new MoggManiac(this);
    }
}

class MoggManiacDealDamageEffect extends OneShotEffect {

    public MoggManiacDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target opponent or planeswalker";
    }

    public MoggManiacDealDamageEffect(final MoggManiacDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public MoggManiacDealDamageEffect copy() {
        return new MoggManiacDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            game.damagePlayerOrPlaneswalker(targetPointer.getFirst(game, source), amount, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
