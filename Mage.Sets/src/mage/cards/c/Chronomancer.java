package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Chronomancer extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Chronomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.NECRON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Atomic Transmutation -- {1}, {T}, Sacrifice another artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability.withFlavorWord("Atomic Transmutation"));

        // Unearth {2}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private Chronomancer(final Chronomancer card) {
        super(card);
    }

    @Override
    public Chronomancer copy() {
        return new Chronomancer(this);
    }
}
