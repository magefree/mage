package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author cbt33, BetaSteward (PastInFlames)
 */
public final class Recoup extends CardImpl {

    private static final FilterCard filter = new FilterCard("sorcery card");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public Recoup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Target sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.getSpellAbility().addEffect(new GainFlashbackTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // Flashback {3}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}")));
    }

    private Recoup(final Recoup card) {
        super(card);
    }

    @Override
    public Recoup copy() {
        return new Recoup(this);
    }
}
