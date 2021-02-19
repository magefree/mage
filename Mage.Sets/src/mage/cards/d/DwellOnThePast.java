package mage.cards.d;

import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DwellOnThePast extends CardImpl {

    public DwellOnThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(4));
    }

    private DwellOnThePast(final DwellOnThePast card) {
        super(card);
    }

    @Override
    public DwellOnThePast copy() {
        return new DwellOnThePast(this);
    }
}
