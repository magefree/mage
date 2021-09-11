package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DryadsRevival extends CardImpl {

    public DryadsRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Return target card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());

        // Flashback {4}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl<>("{4}{G}"), TimingRule.SORCERY));
    }

    private DryadsRevival(final DryadsRevival card) {
        super(card);
    }

    @Override
    public DryadsRevival copy() {
        return new DryadsRevival(this);
    }
}
