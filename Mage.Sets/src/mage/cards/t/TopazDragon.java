package mage.cards.t;

import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TopazDragon extends AdventureCard {

    public TopazDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{B}{B}",
                "Entropic Cloud",
                new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Topaz Dragon
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Entropic Cloud
        // Creatures you control gain deathtouch until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityAllEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));

        finalizeCard();
    }

    private TopazDragon(final TopazDragon card) {
        super(card);
    }

    @Override
    public TopazDragon copy() {
        return new TopazDragon(this);
    }
}
