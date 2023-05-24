package mage.cards.m;

import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author North, noxx
 */
public final class MayorOfAvabruck extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.HUMAN, "Human creatures");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("Werewolf and Wolf creatures");

    static {
        filter2.add(Predicates.or(
                SubType.WEREWOLF.getPredicate(),
                SubType.WOLF.getPredicate()
        ));
    }

    public MayorOfAvabruck(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ADVISOR, SubType.WEREWOLF}, "{1}{G}",
                "Howlpack Alpha",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G"
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 3);

        // Other Human creatures you control get +1/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Mayor of Avabruck.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Howlpack Alpha
        // Other Werewolf and Wolf creatures you control get +1/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        ).setText("each other creature you control that's a Werewolf or a Wolf gets +1/+1")));

        // At the beginning of your end step, create a 2/2 green Wolf creature token.
        this.getRightHalfCard().addAbility(new BeginningOfYourEndStepTriggeredAbility(
                new CreateTokenEffect(new WolfToken()), false
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Howlpack Alpha.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private MayorOfAvabruck(final MayorOfAvabruck card) {
        super(card);
    }

    @Override
    public MayorOfAvabruck copy() {
        return new MayorOfAvabruck(this);
    }
}
