package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnOfTheWildspeaker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Human creatures");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public ReturnOfTheWildspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Choose one —
        // • Draw cards equal to the greatest power among non-Human creatures you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(ReturnOfTheWildspeakerValue.instance)
                .setText("draw cards equal to the greatest power among non-Human creatures you control"));

        // • Non-Human creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new BoostControlledEffect(3, 3, Duration.EndOfTurn, filter)
        ));
    }

    private ReturnOfTheWildspeaker(final ReturnOfTheWildspeaker card) {
        super(card);
    }

    @Override
    public ReturnOfTheWildspeaker copy() {
        return new ReturnOfTheWildspeaker(this);
    }
}

enum ReturnOfTheWildspeakerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield()
                .getAllActivePermanents(sourceAbility.getControllerId())
                .stream()
                .filter(permanent1 -> permanent1.isCreature(game))
                .filter(permanent -> !permanent.hasSubtype(SubType.HUMAN, game))
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}