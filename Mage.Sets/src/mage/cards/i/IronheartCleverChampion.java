package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IronheartCleverChampion extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("noncreature spells you cast");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public IronheartCleverChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Noncreature spells you cast have improvise.
        ImproviseAbility improviseAbility = new ImproviseAbility();
        improviseAbility.setRuleAtTheTop(false);
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(improviseAbility, filter)));
    }

    private IronheartCleverChampion(final IronheartCleverChampion card) {
        super(card);
    }

    @Override
    public IronheartCleverChampion copy() {
        return new IronheartCleverChampion(this);
    }
}
