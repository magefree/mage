package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class FirstOrderDreadnought extends CardImpl {

    public FirstOrderDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");
        
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // {2}{B}, {T}: Destroy target creature.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FirstOrderDreadnought(final FirstOrderDreadnought card) {
        super(card);
    }

    @Override
    public FirstOrderDreadnought copy() {
        return new FirstOrderDreadnought(this);
    }
}
