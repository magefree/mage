package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MysticRetrieval extends CardImpl {

    public MysticRetrieval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Return target instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));

        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}")));
    }

    private MysticRetrieval(final MysticRetrieval card) {
        super(card);
    }

    @Override
    public MysticRetrieval copy() {
        return new MysticRetrieval(this);
    }
}
