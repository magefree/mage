package mage.cards.c;

import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CheekyHouseMouse extends AdventureCard {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 3));
    }

    public CheekyHouseMouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MOUSE}, "{W}",
                "Squeak By",
                new CardType[]{CardType.SORCERY}, "{W}");

        // Cheeky House-Mouse
        this.getLeftHalfCard().setPT(2, 1);

        // Squeak By
        // Target creature you control gets +1/+1 until end of turn. It can't be blocked by creatures with power 3 or greater this turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getRightHalfCard().getSpellAbility().addEffect(new CantBeBlockedTargetEffect(filter, Duration.EndOfTurn)
                .setText("it can't be blocked by creatures with power 3 or greater this turn"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        finalizeCard();
    }

    private CheekyHouseMouse(final CheekyHouseMouse card) {
        super(card);
    }

    @Override
    public CheekyHouseMouse copy() {
        return new CheekyHouseMouse(this);
    }
}
