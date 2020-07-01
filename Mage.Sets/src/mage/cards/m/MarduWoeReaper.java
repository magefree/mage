
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MarduWoeReaper extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.WARRIOR, "Warrior");

    public MarduWoeReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Mardu Woe-Reaper or another Warrior enters the battlefield under your control, you may exile target creature card from a graveyard. If you do, you gain 1 life.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ExileTargetEffect("exile target creature card from a graveyard.")
                , filter, true, true
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("If you do,"));
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(ability);
    }

    private MarduWoeReaper(final MarduWoeReaper card) {
        super(card);
    }

    @Override
    public MarduWoeReaper copy() {
        return new MarduWoeReaper(this);
    }
}
