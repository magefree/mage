package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainControlAllUntapGainHasteEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author muz
 */
public final class BroadcastTakeover extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public BroadcastTakeover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");

        // Gain control of all artifacts your opponents control until end of turn. Untap them. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new GainControlAllUntapGainHasteEffect(filter).withTextOptions("those creatures"));
    }

    private BroadcastTakeover(final BroadcastTakeover card) {
        super(card);
    }

    @Override
    public BroadcastTakeover copy() {
        return new BroadcastTakeover(this);
    }
}
