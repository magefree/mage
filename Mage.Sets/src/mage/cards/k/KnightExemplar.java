package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class KnightExemplar extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.KNIGHT, "Knight creatures");

    public KnightExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Other Knight creatures you control get +1/+1 and are indestructible.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have indestructible"));
        this.addAbility(ability);
    }

    private KnightExemplar(final KnightExemplar card) {
        super(card);
    }

    @Override
    public KnightExemplar copy() {
        return new KnightExemplar(this);
    }
}
