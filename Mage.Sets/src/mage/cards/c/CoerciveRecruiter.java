package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
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
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoerciveRecruiter extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.PIRATE, "Pirate");

    public CoerciveRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Coercive Recruiter or another Pirate enters the battlefield under your control, gain control of target creature until end of turn. Untap that creature. Until end of turn, it gains haste and becomes a Pirate in addition to its other types.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), filter, false, true
        );
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature."));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, it gains haste"));
        ability.addEffect(new AddCardSubTypeTargetEffect(
                SubType.PIRATE, Duration.EndOfTurn
        ).setText("and becomes a Pirate in addition to its other types"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CoerciveRecruiter(final CoerciveRecruiter card) {
        super(card);
    }

    @Override
    public CoerciveRecruiter copy() {
        return new CoerciveRecruiter(this);
    }
}
