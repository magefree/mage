package mage.cards.a;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class AimForTheHead extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ZOMBIE, "Zombie");

    public AimForTheHead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one —
        // • Exile target Zombie.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // • Target opponent exiles two cards from their hand.
        Mode mode = new Mode(new ExileFromZoneTargetEffect(
                Zone.HAND, StaticFilters.FILTER_CARD_CARDS, 2, false
        ));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private AimForTheHead(final AimForTheHead card) {
        super(card);
    }

    @Override
    public AimForTheHead copy() {
        return new AimForTheHead(this);
    }
}
