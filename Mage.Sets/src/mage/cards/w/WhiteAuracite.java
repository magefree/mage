package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhiteAuracite extends CardImpl {

    public WhiteAuracite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}{W}");

        // When this artifact enters, exile target nonland permanent an opponent controls until this artifact leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private WhiteAuracite(final WhiteAuracite card) {
        super(card);
    }

    @Override
    public WhiteAuracite copy() {
        return new WhiteAuracite(this);
    }
}
