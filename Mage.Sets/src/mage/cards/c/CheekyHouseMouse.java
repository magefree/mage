package mage.cards.c;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{W}", "Squeak By", "{W}");

        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Squeak By
        // Target creature you control gets +1/+1 until end of turn. It can't be blocked by creatures with power 3 or greater this turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(1, 1));
        this.getSpellCard().getSpellAbility().addEffect(new CantBeBlockedTargetEffect(filter, Duration.EndOfTurn)
                .setText("it can't be blocked by creatures with power 3 or greater this turn"));
        this.getSpellCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        this.finalizeAdventure();
    }

    private CheekyHouseMouse(final CheekyHouseMouse card) {
        super(card);
    }

    @Override
    public CheekyHouseMouse copy() {
        return new CheekyHouseMouse(this);
    }
}
