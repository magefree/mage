package mage.cards.v;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class Vanishment extends CardImpl {

    public Vanishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Put target nonland permanent on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Miracle {U}
        this.addAbility(new MiracleAbility("{U}"));
    }

    private Vanishment(final Vanishment card) {
        super(card);
    }

    @Override
    public Vanishment copy() {
        return new Vanishment(this);
    }
}
