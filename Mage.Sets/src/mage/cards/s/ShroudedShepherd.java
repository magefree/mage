package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShroudedShepherd extends AdventureCard {

    public ShroudedShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WARRIOR}, "{1}{W}",
                "Cleave Shadows",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Shrouded Shepherd
        this.getLeftHalfCard().setPT(2, 2);

        // When Veiled Shepherd enters the battlefield, target creature you control gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Cleave Shadows
        // Creatures your opponents control get -1/-1 until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));

        finalizeCard();
    }

    private ShroudedShepherd(final ShroudedShepherd card) {
        super(card);
    }

    @Override
    public ShroudedShepherd copy() {
        return new ShroudedShepherd(this);
    }
}
