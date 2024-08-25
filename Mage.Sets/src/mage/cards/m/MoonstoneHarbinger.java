package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLoseLifeYourTurnTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonstoneHarbinger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BAT, "");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.BAT, "");

    public MoonstoneHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever you gain or lose life during your turn, Bats you control get +1/+0 and gain deathtouch until end of turn. This ability triggers only once each turn.
        Ability ability = new GainLoseLifeYourTurnTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter
        ).setText("Bats you control get +1/+0")).setTriggersLimitEachTurn(1);
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, filter2
        ).setText("and gain deathtouch until end of turn"));
        this.addAbility(ability);
    }

    private MoonstoneHarbinger(final MoonstoneHarbinger card) {
        super(card);
    }

    @Override
    public MoonstoneHarbinger copy() {
        return new MoonstoneHarbinger(this);
    }
}
