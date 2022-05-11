package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KeskitTheFleshSculptor extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("other artifacts and/or creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public KeskitTheFleshSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice three other artifacts and/or creatures: Look at the top three cards of your library.
        // Put two of them into your hand and the other into your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(3, 2, PutCards.HAND, PutCards.GRAVEYARD),
                new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, filter)));
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private KeskitTheFleshSculptor(final KeskitTheFleshSculptor card) {
        super(card);
    }

    @Override
    public KeskitTheFleshSculptor copy() {
        return new KeskitTheFleshSculptor(this);
    }
}
