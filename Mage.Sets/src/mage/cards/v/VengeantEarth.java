package mage.cards.v;

import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengeantEarth extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("creature or land you control");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public VengeantEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature or land you control becomes a 4/4 Elemental creature with haste in addition to its other types until end of turn. It must be blocked this turn if able.
        this.getSpellAbility().addEffect(new BecomesCreatureTargetEffect(new CreatureToken(
                4, 4, "4/4 Elemental creature with haste", SubType.ELEMENTAL
        ).withAbility(HasteAbility.getInstance()), false, false, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new MustBeBlockedByAtLeastOneTargetEffect().setText("it must be blocked this turn if able"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private VengeantEarth(final VengeantEarth card) {
        super(card);
    }

    @Override
    public VengeantEarth copy() {
        return new VengeantEarth(this);
    }
}
