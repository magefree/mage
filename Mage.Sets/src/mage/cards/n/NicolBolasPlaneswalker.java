
package mage.cards.n;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author North
 */
public final class NicolBolasPlaneswalker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public NicolBolasPlaneswalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);

        this.setStartingLoyalty(5);

        // +3: Destroy target noncreature permanent.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), 3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
        // -2: Gain control of target creature.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.Custom), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // -9: Nicol Bolas, Planeswalker deals 7 damage to target player. That player discards seven cards, then sacrifices seven permanents.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -9);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addEffect(new DiscardTargetEffect(7)
                .setText("That player or that planeswalker's controller discards seven cards")
        );
        ability.addEffect(new SacrificeEffect(new FilterPermanent(), 7, "then")
                .setText("then sacrifices seven permanents")
        );
        this.addAbility(ability);
    }

    private NicolBolasPlaneswalker(final NicolBolasPlaneswalker card) {
        super(card);
    }

    @Override
    public NicolBolasPlaneswalker copy() {
        return new NicolBolasPlaneswalker(this);
    }
}
