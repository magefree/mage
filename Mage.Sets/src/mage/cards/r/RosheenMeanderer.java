package mage.cards.r;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.conditional.XCostManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class RosheenMeanderer extends CardImpl {

    public RosheenMeanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R/G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {T}: Add {C}{C}{C}{C}. Spend this mana only on costs that contain {X}.
        this.addAbility(new RosheenMeandererManaAbility());
    }

    private RosheenMeanderer(final RosheenMeanderer card) {
        super(card);
    }

    @Override
    public RosheenMeanderer copy() {
        return new RosheenMeanderer(this);
    }
}

class RosheenMeandererManaAbility extends ActivatedManaAbilityImpl {

    RosheenMeandererManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(new RosheenMeandererConditionalMana()), new TapSourceCost());
        this.netMana.add(Mana.ColorlessMana(4));
    }

    private RosheenMeandererManaAbility(RosheenMeandererManaAbility ability) {
        super(ability);
    }

    @Override
    public RosheenMeandererManaAbility copy() {
        return new RosheenMeandererManaAbility(this);
    }
}

class RosheenMeandererConditionalMana extends ConditionalMana {

    RosheenMeandererConditionalMana() {
        super(Mana.ColorlessMana(4));
        staticText = "Spend this mana only on costs that contain {X}";
        addCondition(new XCostManaCondition());
    }
}
