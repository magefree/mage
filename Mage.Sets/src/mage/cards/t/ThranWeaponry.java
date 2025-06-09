
package mage.cards.t;

import mage.MageItem;
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
import mage.constants.Layer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

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
        Ability ability = new SimpleActivatedAbility(new ThranWeaponryEffect(), new ManaCostsImpl<>("{2}"));
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

    private ThranWeaponryEffect(final ThranWeaponryEffect effect) {
        super(effect);
    }

    @Override
    public ThranWeaponryEffect copy() {
        return new ThranWeaponryEffect(this);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent thranWeaponry = game.getPermanent(source.getSourceId());
        if (thranWeaponry == null || !thranWeaponry.isTapped()) {
            this.discard();
            return false;
        }
        return super.queryAffectedObjects(layer, source, game, affectedObjects);
    }
}
