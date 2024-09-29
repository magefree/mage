package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class CastOut extends CardImpl {

    public CastOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Cast Out enters the battlefield, exile target nonland permanent an opponent controls until Cast Out leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // Cycling {W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{W}")));

    }

    private CastOut(final CastOut card) {
        super(card);
    }

    @Override
    public CastOut copy() {
        return new CastOut(this);
    }
}
