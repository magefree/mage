package mage.cards.e;

import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.keyword.ReflexAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */
public final class EmptyNight extends CardImpl {

    public EmptyNight(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.addAbility(new ReflexAbility(this, "{4}{W}{W}{W}", new ExileAllEffect(new FilterCreatureOrPlaneswalkerPermanent("creatures and planeswalkers."))));

        this.getSpellAbility().addEffect(new ExileAllEffect(new FilterCreatureOrPlaneswalkerPermanent("creatures and planeswalkers.")));
    }


    public EmptyNight(final EmptyNight card) {
        super(card);
    }

    @Override
    public EmptyNight copy() {
        return new EmptyNight(this);
    }
}
