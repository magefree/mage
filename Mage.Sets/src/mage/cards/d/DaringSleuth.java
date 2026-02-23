package mage.cards.d;

import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DaringSleuth extends TransformingDoubleFacedCard {

    public DaringSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE}, "{1}{U}",
                "Bearer of Overwhelming Truths",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "U");

        // Daring Sleuth
        this.getLeftHalfCard().setPT(2, 1);

        // When you sacrifice a Clue, transform Daring Sleuth.
        this.getLeftHalfCard().addAbility(new SacrificePermanentTriggeredAbility(
                new TransformSourceEffect(), StaticFilters.FILTER_CONTROLLED_CLUE
        ).setTriggerPhrase("When you sacrifice a Clue, "));

        // Bearer of Overwhelming Truths
        this.getRightHalfCard().setPT(3, 2);

        // Prowess
        this.getRightHalfCard().addAbility(new ProwessAbility());

        // Whenever Bearer of Overwhelming Truths deals combat damage to a player, investigate.
        this.getRightHalfCard().addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new InvestigateEffect(), false));
    }

    private DaringSleuth(final DaringSleuth card) {
        super(card);
    }

    @Override
    public DaringSleuth copy() {
        return new DaringSleuth(this);
    }
}
