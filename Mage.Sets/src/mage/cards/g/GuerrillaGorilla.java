package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GuerrillaGorilla extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("noncreature artifact or noncreature enchantment");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public GuerrillaGorilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Sacrifice this creature: Destroy target noncreature artifact or noncreature enchantment. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new DestroyTargetEffect(), new SacrificeSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GuerrillaGorilla(final GuerrillaGorilla card) {
        super(card);
    }

    @Override
    public GuerrillaGorilla copy() {
        return new GuerrillaGorilla(this);
    }
}
