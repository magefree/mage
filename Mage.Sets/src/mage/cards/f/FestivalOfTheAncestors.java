package mage.cards.f;

import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

public class FestivalOfTheAncestors extends CardImpl {
    public FestivalOfTheAncestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}");

        //Target player gains 5 life.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public FestivalOfTheAncestors(final FestivalOfTheAncestors card) {
        super(card);
    }

    @Override
    public FestivalOfTheAncestors copy() {
        return new FestivalOfTheAncestors(this);
    }
}
