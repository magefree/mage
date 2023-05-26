
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
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
import mage.constants.SuperType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class DarthSidiousSithLord extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DarthSidiousSithLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{4}{U}{B}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SIDIOUS);

        this.setStartingLoyalty(5);

        // +3: Destroy target noncreature permanent.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), +3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -2: Gain control of target creature.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.Custom), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -9: Darth Sidious deals 7 damage to target player. That player discards seven cards, then sacrificies seven permanents.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -9);
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new DiscardTargetEffect(7));
        ability.addEffect(new SacrificeEffect(new FilterPermanent(), 7, "then"));
        this.addAbility(ability);
    }

    private DarthSidiousSithLord(final DarthSidiousSithLord card) {
        super(card);
    }

    @Override
    public DarthSidiousSithLord copy() {
        return new DarthSidiousSithLord(this);
    }
}
