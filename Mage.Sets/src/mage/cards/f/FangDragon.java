package mage.cards.f;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangDragon extends AdventureCard {

    public FangDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{5}{R}{R}",
                "Forktail Sweep",
                new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Fang Dragon
        this.getLeftHalfCard().setPT(6, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Forktail Sweep deals 1 damage to each creature you don't control.
        this.getRightHalfCard().getSpellAbility().addEffect(new DamageAllEffect(
                1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL
        ));

        finalizeCard();
    }

    private FangDragon(final FangDragon card) {
        super(card);
    }

    @Override
    public FangDragon copy() {
        return new FangDragon(this);
    }
}
