package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaywireMite extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("noncreature artifact or noncreature enchantment");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public HaywireMite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Haywire Mite dies, you gain 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(2)));

        // {G}, Sacrifice Haywire Mite: Exile target noncreature artifact or noncreature enchantment.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HaywireMite(final HaywireMite card) {
        super(card);
    }

    @Override
    public HaywireMite copy() {
        return new HaywireMite(this);
    }
}
