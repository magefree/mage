package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
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
 * @author jeffwadsworth
 */
public final class SupremeInquisitor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SupremeInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Tap five untapped Wizards you control: Search target player's library for up to five cards and exile them. Then that player shuffles their library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryAndExileTargetEffect(5, true),
                new TapTargetCost(new TargetControlledPermanent(5, filter))
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private SupremeInquisitor(final SupremeInquisitor card) {
        super(card);
    }

    @Override
    public SupremeInquisitor copy() {
        return new SupremeInquisitor(this);
    }
}
