package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Tuomas-Matti Soikkeli
 */

public final class Gleemox extends CardImpl {

    public Gleemox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.addAbility(new AnyColorManaAbility());
        this.addAbility(new SimpleStaticAbility(new InfoEffect("This card is banned.")));
    }

    private Gleemox(final Gleemox card) {
        super(card);
    }

    @Override
    public Gleemox copy() {
        return new Gleemox(this);
    }
}
