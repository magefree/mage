package mage.abilities.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.mana.ManaEffect;
import mage.constants.Zone;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class AddEachControlledColorManaAbility extends ActivatedManaAbilityImpl {

    public AddEachControlledColorManaAbility() {
        super(Zone.BATTLEFIELD, new AddEachControlledColorManaEffect(), new TapSourceCost());
        this.addHint(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getHint());
    }

    private AddEachControlledColorManaAbility(final AddEachControlledColorManaAbility ability) {
        super(ability);
    }

    @Override
    public AddEachControlledColorManaAbility copy() {
        return new AddEachControlledColorManaAbility(this);
    }
}

class AddEachControlledColorManaEffect extends ManaEffect {

    AddEachControlledColorManaEffect() {
        super();
        staticText = "for each color among permanents you control, add one mana of that color";
    }

    private AddEachControlledColorManaEffect(final AddEachControlledColorManaEffect effect) {
        super(effect);
    }

    @Override
    public AddEachControlledColorManaEffect copy() {
        return new AddEachControlledColorManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        return Mana.fromColor(ColorsAmongControlledPermanentsCount.ALL_PERMANENTS.getAllControlledColors(game, source));
    }
}
