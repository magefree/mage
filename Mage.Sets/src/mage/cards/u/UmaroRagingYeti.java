package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmaroRagingYeti extends CardImpl {

    public UmaroRagingYeti(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.YETI);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, choose one at random --
        // * Other creatures you control get +3/+0 and gain trample until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostControlledEffect(
                3, 0, Duration.EndOfTurn, true
        ).setText("other creatures you control get +3/+0"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        ).setText("and gain trample until end of turn"));
        ability.getModes().setRandom(true);

        // * Discard your hand, then draw four cards.
        ability.addMode(new Mode(new DiscardHandControllerEffect())
                .addEffect(new DrawCardSourceControllerEffect(4).concatBy(", then")));

        // * Umaro deals 5 damage to any target.
        ability.addMode(new Mode(new DamageTargetEffect(5)).addTarget(new TargetAnyTarget()));
        this.addAbility(ability);
    }

    private UmaroRagingYeti(final UmaroRagingYeti card) {
        super(card);
    }

    @Override
    public UmaroRagingYeti copy() {
        return new UmaroRagingYeti(this);
    }
}
