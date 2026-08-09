package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.YouControlPermanentCondition;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nandmp
 */
public final class EvilsThrall extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent(SubType.VILLAIN, "Villain");

    static {
        filter.add(EvilsThrallPredicate.instance);
    }

    public EvilsThrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. If you control a Villain with greater mana value than that creature, gain control of that creature until the end of your next turn instead. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.UntilEndOfYourNextTurn),
                new GainControlTargetEffect(Duration.EndOfTurn),
                new LockedInCondition(new YouControlPermanentCondition(filter)),
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

enum EvilsThrallPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent creature = game.getPermanent(input.getSource().getFirstTarget());
        return creature != null
                && input.getObject().getManaValue() > creature.getManaValue();
    }
}
