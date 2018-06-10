
package mage.cards.r;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public final class RosheenMeanderer extends CardImpl {

    public RosheenMeanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R/G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}: Add {C}{C}{C}{C}. Spend this mana only on costs that contain {X}.
        this.addAbility(new RosheenMeandererManaAbility());

    }

    public RosheenMeanderer(final RosheenMeanderer card) {
        super(card);
    }

    @Override
    public RosheenMeanderer copy() {
        return new RosheenMeanderer(this);
    }
}

class RosheenMeandererManaAbility extends BasicManaAbility {

    RosheenMeandererManaAbility() {
        super(new BasicManaEffect(new RosheenMeandererConditionalMana()));
        this.netMana.add(Mana.ColorlessMana(4));
    }

    RosheenMeandererManaAbility(RosheenMeandererManaAbility ability) {
        super(ability);
    }

    @Override
    public RosheenMeandererManaAbility copy() {
        return new RosheenMeandererManaAbility(this);
    }
}

class RosheenMeandererConditionalMana extends ConditionalMana {

    public RosheenMeandererConditionalMana() {
        super(Mana.ColorlessMana(4));
        staticText = "Spend this mana only on costs that contain {X}";
        addCondition(new RosheenMeandererManaCondition());
    }
}

class RosheenMeandererManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityType.SPELL == source.getAbilityType()) {
            MageObject object = game.getObject(source.getSourceId());
            return object != null
                    && object.getManaCost().getText().contains("X");

        } else {
            return source.getManaCosts().getText().contains("X");
        }
    }
}
