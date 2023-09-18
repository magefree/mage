
package mage.cards.c;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class CultivatorDrone extends CardImpl {

    public CultivatorDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new CultivatorDroneManaBuilder()));
    }

    private CultivatorDrone(final CultivatorDrone card) {
        super(card);
    }

    @Override
    public CultivatorDrone copy() {
        return new CultivatorDrone(this);
    }
}

class CultivatorDroneManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CultivatorDroneConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}";
    }
}

class CultivatorDroneConditionalMana extends ConditionalMana {

    public CultivatorDroneConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}";
        addCondition(new CultivatorDroneManaCondition());
    }
}

class CultivatorDroneManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            if (object != null && object.getColor(game).isColorless()) {
                return true;
            }
        }
        if (source instanceof ActivatedAbility) {
            Permanent object = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (object != null && object.getColor(game).isColorless()) {
                return true;
            }
        }
        if (costToPay instanceof ManaCost) {
            return costToPay.getText().contains("{C}");
        }
        return false;
    }
}
