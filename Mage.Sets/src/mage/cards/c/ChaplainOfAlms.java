package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaplainOfAlms extends TransformingDoubleFacedCard {

    public ChaplainOfAlms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{W}",
                "Chapel Shieldgeist",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.CLERIC}, "W");

        // Chaplain of Alms
        this.getLeftHalfCard().setPT(1, 1);

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Ward {1}
        this.getLeftHalfCard().addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Disturb {3}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{W}"));

        // Chapel Shieldgeist
        this.getRightHalfCard().setPT(2, 1);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Each creature you control has ward {1}.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control has ward {1}. " +
                "<i>(Whenever it becomes the target of a spell or ability an opponent controls, counter it unless that player pays 1.)</i>")));

        // If Chapel Shieldgeist would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private ChaplainOfAlms(final ChaplainOfAlms card) {
        super(card);
    }

    @Override
    public ChaplainOfAlms copy() {
        return new ChaplainOfAlms(this);
    }
}
