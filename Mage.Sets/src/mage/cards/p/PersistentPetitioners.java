package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PersistentPetitioners extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped Advisors you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.ADVISOR.getPredicate());
    }

    public PersistentPetitioners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}, {T}: Target player puts the top card of their library into their graveyard.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsTargetEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Tap four untapped Advisors you control: Target player puts the top twelve cards of their library into their graveyard.
        ability = new SimpleActivatedAbility(
                new MillCardsTargetEffect(12),
                new TapTargetCost(new TargetControlledPermanent(
                        4, 4, filter, true
                ))
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // A deck can have any number of cards named Persistent Petitioners.
        this.getSpellAbility().addEffect(new InfoEffect("A deck can have any number of cards named Persistent Petitioners."));
    }

    private PersistentPetitioners(final PersistentPetitioners card) {
        super(card);
    }

    @Override
    public PersistentPetitioners copy() {
        return new PersistentPetitioners(this);
    }
}
