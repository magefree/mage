
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DeathmarkPrelate extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("Zombie");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("non-Zombie creature");

    static {
        filter1.add(SubType.ZOMBIE.getPredicate());
        filter2.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public DeathmarkPrelate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{B}, {tap}, Sacrifice a Zombie: Destroy target non-Zombie creature. It can't be regenerated. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter1)));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    private DeathmarkPrelate(final DeathmarkPrelate card) {
        super(card);
    }

    @Override
    public DeathmarkPrelate copy() {
        return new DeathmarkPrelate(this);
    }
}
