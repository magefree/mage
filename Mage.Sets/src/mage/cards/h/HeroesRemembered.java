package mage.cards.h;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

public final class HeroesRemembered extends CardImpl {

    public HeroesRemembered(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{W}{W}{W}");

        //You gain 20 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(20));
        //Suspend 10-{W}
        this.addAbility(new SuspendAbility(10, new ManaCostsImpl<>("{W}"), this));
    }

    private HeroesRemembered(final HeroesRemembered card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new HeroesRemembered(this);
    }
}
