package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Stirge extends CardImpl {

    public Stirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.BAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Stirge can't block.
        this.addAbility(new CantBlockAbility());

        // Blood Drain — {1}{B}, Pay 1 life, Sacrifice Stirge: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.withFlavorWord("Blood Drain"));
    }

    private Stirge(final Stirge card) {
        super(card);
    }

    @Override
    public Stirge copy() {
        return new Stirge(this);
    }
}
