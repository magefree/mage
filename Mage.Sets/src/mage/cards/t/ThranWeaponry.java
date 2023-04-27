
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class ThranWeaponry extends CardImpl {

    public ThranWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Echo {4}
        this.addAbility(new EchoAbility("{4}"));
        // You may choose not to untap Thran Weaponry during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}, {tap}: All creatures get +2/+2 for as long as Thran Weaponry remains tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ThranWeaponryEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }

    private ThranWeaponry(final ThranWeaponry card) {
        super(card);
    }

    @Override
    public ThranWeaponry copy() {
        return new ThranWeaponry(this);
    }
}

class ThranWeaponryEffect extends BoostAllEffect{

    public ThranWeaponryEffect() {
        super(2, 2, Duration.WhileOnBattlefield);
        staticText = "All creatures get +2/+2 for as long as Thran Weaponry remains tapped";
    }

    public ThranWeaponryEffect(final ThranWeaponryEffect effect) {
        super(effect);
    }

    @Override
    public ThranWeaponryEffect copy() {
        return new ThranWeaponryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ThranWeaponry = game.getPermanent(source.getSourceId());
        if (ThranWeaponry != null) {
            if (ThranWeaponry.isTapped()) {
                super.apply(game, source);
                return true;
            } else {
                used = true;
            }
        }
        return false;
    }
}
