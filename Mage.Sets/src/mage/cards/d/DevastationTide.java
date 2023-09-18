package mage.cards.d;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class DevastationTide extends CardImpl {

    public DevastationTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return all nonland permanents to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(new FilterNonlandPermanent("nonland permanents")));

        // Miracle {1}{U}
        this.addAbility(new MiracleAbility("{1}{U}"));
    }

    private DevastationTide(final DevastationTide card) {
        super(card);
    }

    @Override
    public DevastationTide copy() {
        return new DevastationTide(this);
    }
}
