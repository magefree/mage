

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public final class EtherswornAdjudicator extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public EtherswornAdjudicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{W}{B}, {T}: Destroy target creature or enchantment.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{W}{B}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        // {2}{U}: Untap Ethersworn Adjudicator.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ManaCostsImpl<>("{2}{U}")));
    }

    private EtherswornAdjudicator(final EtherswornAdjudicator card) {
        super(card);
    }

    @Override
    public EtherswornAdjudicator copy() {
        return new EtherswornAdjudicator(this);
    }

}
