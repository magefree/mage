package mage.cards.m;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mirrormade extends CardImpl {

    public Mirrormade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // You may have Mirrormade enter the battlefield as a copy of any artifact or enchantment on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT), true
        ));
    }

    private Mirrormade(final Mirrormade card) {
        super(card);
    }

    @Override
    public Mirrormade copy() {
        return new Mirrormade(this);
    }
}
