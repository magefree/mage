package mage.cards.s;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
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
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShacklesOfTreachery extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.EQUIPMENT, "Equipment attached to it");

    static {
        filter.add(AttachedToSourcePredicate.instance);
    }

    public ShacklesOfTreachery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. Until end of turn, it gains haste and "Whenever this creature deals damage, destroy target Equipment attached to it."
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, it gains haste"));

        TriggeredAbility ability = new DealsDamageSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        ability.setTriggerPhrase("Whenever this creature deals damage, ");

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ability, Duration.EndOfTurn
        ).setText("and \"Whenever this creature deals damage, destroy target Equipment attached to it.\""));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShacklesOfTreachery(final ShacklesOfTreachery card) {
        super(card);
    }

    @Override
    public ShacklesOfTreachery copy() {
        return new ShacklesOfTreachery(this);
    }
}
