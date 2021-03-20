package mage.cards.s;

import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StreamOfConsciousness extends CardImpl {

    public StreamOfConsciousness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(4));
    }

    private StreamOfConsciousness(final StreamOfConsciousness card) {
        super(card);
    }

    @Override
    public StreamOfConsciousness copy() {
        return new StreamOfConsciousness(this);
    }
}
