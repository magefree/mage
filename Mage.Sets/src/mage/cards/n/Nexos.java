package mage.cards.n;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.conditional.XCostManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nexos extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("basic lands");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public Nexos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TYRANID);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Strategic Coordinator -- Basic lands you control have "{T}: Add {C}{C}. Spend this mana only on costs that contain {X}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new NexosManaAbility(), Duration.WhileOnBattlefield, filter
        )).withFlavorWord("Strategic Coordinator"));
    }

    private Nexos(final Nexos card) {
        super(card);
    }

    @Override
    public Nexos copy() {
        return new Nexos(this);
    }
}

class NexosManaAbility extends ActivatedManaAbilityImpl {

    NexosManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(new NexosConditionalMana()), new TapSourceCost());
        this.netMana.add(Mana.ColorlessMana(2));
    }

    private NexosManaAbility(NexosManaAbility ability) {
        super(ability);
    }

    @Override
    public NexosManaAbility copy() {
        return new NexosManaAbility(this);
    }
}

class NexosConditionalMana extends ConditionalMana {

    NexosConditionalMana() {
        super(Mana.ColorlessMana(2));
        staticText = "Spend this mana only on costs that contain {X}";
        addCondition(new XCostManaCondition());
    }
}
