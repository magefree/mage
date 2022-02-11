package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BlindObedience extends CardImpl {

    public BlindObedience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());

        // Artifacts and creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE
        ).setText("artifacts and creatures your opponents control enter the battlefield tapped")));

    }

    private BlindObedience(final BlindObedience card) {
        super(card);
    }

    @Override
    public BlindObedience copy() {
        return new BlindObedience(this);
    }
}
