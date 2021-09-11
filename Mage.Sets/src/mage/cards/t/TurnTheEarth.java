package mage.cards.t;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurnTheEarth extends CardImpl {

    public TurnTheEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose up to three target cards in graveyards. The owners of those cards shuffle them into their libraries. You gain 2 life.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect()
                .setText("choose up to three target cards in graveyards. " +
                        "The owners of those cards shuffle them into their libraries."));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 3));

        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}")));
    }

    private TurnTheEarth(final TurnTheEarth card) {
        super(card);
    }

    @Override
    public TurnTheEarth copy() {
        return new TurnTheEarth(this);
    }
}
