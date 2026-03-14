package mage.cards.o;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class OvergrownZealot extends CardImpl {

    public OvergrownZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add two mana of any one color. Spend this mana only to turn permanents face up.
        this.addAbility(new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2,
                new OvergrownZealotManaBuilder(), true
        ));

    }

    private OvergrownZealot(final OvergrownZealot card) {
        super(card);
    }

    @Override
    public OvergrownZealot copy() {
        return new OvergrownZealot(this);
    }
}

class OvergrownZealotManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OvergrownZealotConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to turn permanents face up";
    }
}

class OvergrownZealotConditionalMana extends ConditionalMana {

    public OvergrownZealotConditionalMana(Mana mana) {
        super(mana);
        addCondition(new OvergrownZealotManaCondition());
    }
}

class OvergrownZealotManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof TurnFaceUpAbility) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            return permanent.isManifested()
                    || permanent.isMorphed()
                    || permanent.isDisguised()
                    || permanent.isCloaked();
        }
        return false;
    }
}
