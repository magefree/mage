package mage.cards.q;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class QarsiDeceiver extends CardImpl {

    public QarsiDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // {T}: Add {C}. Spend this mana only to cast a face-down creature spell, pay a mana cost to turn a manifested creature face up, or pay a morph cost. <i.(A megamorph cost is a morph cost.)</i>
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new QarsiDeceiverManaBuilder()));
    }

    private QarsiDeceiver(final QarsiDeceiver card) {
        super(card);
    }

    @Override
    public QarsiDeceiver copy() {
        return new QarsiDeceiver(this);
    }
}

class QarsiDeceiverManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new QarsiDeceiverConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a face-down creature spell, pay a mana cost to turn a manifested creature face up, or pay a morph cost. <i>(A megamorph cost is a morph cost.)</i>";
    }
}

class QarsiDeceiverConditionalMana extends ConditionalMana {

    public QarsiDeceiverConditionalMana(Mana mana) {
        super(mana);
        addCondition(new QarsiDeceiverManaCondition());
    }
}

class QarsiDeceiverManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            return ((SpellAbility) source).getSpellAbilityCastMode().isFaceDown()
                    && ((SpellAbility) source).getCharacteristics(game).isCreature(game);
        }
        // morph cost or turn manifest creature face up
        if (source instanceof TurnFaceUpAbility) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent == null) {
                return false;
            }
            return permanent.isManifested() || permanent.isMorphed();
        }
        return false;
    }
}
