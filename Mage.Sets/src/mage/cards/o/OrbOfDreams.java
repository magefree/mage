package mage.cards.o;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrbOfDreams extends CardImpl {

    public OrbOfDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Permanents enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(StaticFilters.FILTER_PERMANENTS)));
    }

    private OrbOfDreams(final OrbOfDreams card) {
        super(card);
    }

    @Override
    public OrbOfDreams copy() {
        return new OrbOfDreams(this);
    }
}
