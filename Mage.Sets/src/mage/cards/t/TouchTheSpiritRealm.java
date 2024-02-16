package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Addictiveme
 */
public final class TouchTheSpiritRealm extends CardImpl {

    public TouchTheSpiritRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Touch the Spirit Realm enters the battlefield, exile up to one target artifact or creature until
        // Touch the Spirit Realm leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);
        
        // Channel - {1}{W}, Discard Touch the Spirit Realm: Exile target artifact or creature.
        // Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability channelAbility = new ChannelAbility("{1}{W}",
                new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        channelAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(channelAbility);
    }

    private TouchTheSpiritRealm(final TouchTheSpiritRealm card) {
        super(card);
    }

    @Override
    public TouchTheSpiritRealm copy() {
        return new TouchTheSpiritRealm(this);
    }
}
