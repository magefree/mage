package mage.cards.d;

import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaringDiscovery extends CardImpl {

    public DaringDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Up to three target creatures can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));

        // Discover 4.
        this.getSpellAbility().addEffect(new DiscoverEffect(4).concatBy("<br>"));
    }

    private DaringDiscovery(final DaringDiscovery card) {
        super(card);
    }

    @Override
    public DaringDiscovery copy() {
        return new DaringDiscovery(this);
    }
}
