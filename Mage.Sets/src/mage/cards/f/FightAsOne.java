package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FightAsOne extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.HUMAN);
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent("non-Human creature you control");

    static {
        filter2.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public FightAsOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one or both—
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target Human creature you control gets +1/+1 and gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1)
                .setText("Target Human creature you control gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Target non-Human creature you control gets +1/+1 and gains indestructible until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(1, 1)
                .setText("Target non-Human creature you control gets +1/+1"));
        mode.addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible until end of turn"));
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private FightAsOne(final FightAsOne card) {
        super(card);
    }

    @Override
    public FightAsOne copy() {
        return new FightAsOne(this);
    }
}
