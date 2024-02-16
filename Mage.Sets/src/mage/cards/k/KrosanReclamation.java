package mage.cards.k;

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
 * @author fireshoes
 */
public final class KrosanReclamation extends CardImpl {

    public KrosanReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target player shuffles up to two target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(2));

        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}")));
    }

    private KrosanReclamation(final KrosanReclamation card) {
        super(card);
    }

    @Override
    public KrosanReclamation copy() {
        return new KrosanReclamation(this);
    }
}
