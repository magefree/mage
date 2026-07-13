package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.Condition;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nandmp
 */
public final class EvilsThrall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.VILLAIN, "a Villain you control");
    private static final Condition condition = (game, source) -> {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        return creature != null && game.getBattlefield()
                .getAllActivePermanents(filter, source.getControllerId(), game)
                .stream()
                .anyMatch(villain -> villain.getManaValue() > creature.getManaValue());
    };

    public EvilsThrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. If you control a Villain with greater mana value than that creature, gain control of that creature until the end of your next turn instead. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.UntilEndOfYourNextTurn),
                new GainControlTargetEffect(Duration.EndOfTurn),
                new LockedInCondition(condition),
                "gain control of target creature until end of turn. If you control a Villain with "
                        + "greater mana value than that creature, gain control of that creature until "
                        + "the end of your next turn instead"
        ));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
    }

    private EvilsThrall(final EvilsThrall card) {
        super(card);
    }

    @Override
    public EvilsThrall copy() {
        return new EvilsThrall(this);
    }
}
