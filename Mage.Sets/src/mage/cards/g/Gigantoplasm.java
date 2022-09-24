
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.functions.CopyApplier;

/**
 *
 * @author fireshoes
 */
public final class Gigantoplasm extends CardImpl {

    public Gigantoplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Gigantoplasm enter the battlefield as a copy of any creature on the battlefield, except it has "{X}: This creature has base power and toughness X/X."
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new GigantoplasmCopyApplier());
        effect.setText("a copy of any creature on the battlefield, except it has \"{X}: This creature has base power and toughness X/X.\"");
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    private Gigantoplasm(final Gigantoplasm card) {
        super(card);
    }

    @Override
    public Gigantoplasm copy() {
        return new Gigantoplasm(this);
    }
}

class GigantoplasmCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        DynamicValue variableMana = ManacostVariableValue.REGULAR;
        Effect effect = new SetBasePowerToughnessSourceEffect(variableMana, Duration.WhileOnBattlefield, SubLayer.SetPT_7b, true);
        effect.setText("This creature has base power and toughness X/X");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}"));
        blueprint.getAbilities().add(ability);
        return true;
    }

}
