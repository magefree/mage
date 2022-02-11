package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class MemorysJourney extends CardImpl {

    public MemorysJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target player shuffles up to three target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(3));

        // Flashback {G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{G}")));
    }

    private MemorysJourney(final MemorysJourney card) {
        super(card);
    }

    @Override
    public MemorysJourney copy() {
        return new MemorysJourney(this);
    }
}
