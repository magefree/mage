package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Backfir3
 */
public final class AncientOoze extends CardImpl {

    public AncientOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.OOZE);

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ancient Ooze's power and toughness are each equal to the total converted mana cost of other creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new AncientOozePowerToughnessValue(), Duration.EndOfGame)
                .setText("{this}'s power and toughness are each equal to the total mana value of other creatures you control.")
        ));
    }

    private AncientOoze(final AncientOoze card) {
        super(card);
    }

    @Override
    public AncientOoze copy() {
        return new AncientOoze(this);
    }
}

class AncientOozePowerToughnessValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), sourceAbility.getControllerId(), game)) {
            if (creature != null && !sourceAbility.getSourceId().equals(creature.getId())) {
                value += creature.getManaValue();
            }
        }
        return value;
    }

    @Override
    public AncientOozePowerToughnessValue copy() {
        return new AncientOozePowerToughnessValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "total mana value of other creatures you control";
    }
}
