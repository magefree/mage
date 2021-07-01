package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleverConjurer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent not named Clever Conjurer");

    static {
        filter.add(Predicates.not(new NamePredicate("Clever Conjurer")));
    }

    public CleverConjurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mage Hand — {T}: Untap target permanent not named Clever Conjurer. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.withFlavorWord("Mage Hand"));
    }

    private CleverConjurer(final CleverConjurer card) {
        super(card);
    }

    @Override
    public CleverConjurer copy() {
        return new CleverConjurer(this);
    }
}
