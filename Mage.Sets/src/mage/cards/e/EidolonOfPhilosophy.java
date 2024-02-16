package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class EidolonOfPhilosophy extends CardImpl {

    public EidolonOfPhilosophy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {6}{U}, Sacrifice Eidolon of Philosophy: Draw three cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new ManaCostsImpl<>("{6}{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EidolonOfPhilosophy(final EidolonOfPhilosophy card) {
        super(card);
    }

    @Override
    public EidolonOfPhilosophy copy() {
        return new EidolonOfPhilosophy(this);
    }
}
