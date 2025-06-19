package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class MadDog extends CardImpl {

    public MadDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if Mad Dog didn't attack or come under your control this turn, sacrifice it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceEffect().setText("sacrifice it")
        ).withInterveningIf(MadDogCondition.instance));
    }

    private MadDog(final MadDog card) {
        super(card);
    }

    @Override
    public MadDog copy() {
        return new MadDog(this);
    }
}

enum MadDogCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return !AttackedThisTurnSourceCondition.instance.apply(game, source)
                && Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Permanent::wasControlledFromStartOfControllerTurn)
                .isPresent();
    }

    @Override
    public String toString() {
        return "{this} didn't attack or come under your control this turn";
    }
}
