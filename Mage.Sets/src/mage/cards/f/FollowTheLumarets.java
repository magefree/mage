package mage.cards.f;

import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FollowTheLumarets extends CardImpl {

    public FollowTheLumarets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Infusion -- Look at the top four cards of your library. You may reveal a creature or land card from among them and put it into your hand. If you gained life this turn, you may instead reveal two creature and/or land cards from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new LookLibraryAndPickControllerEffect(
                        4, 2,
                        StaticFilters.FILTER_CARD_CREATURE_OR_LAND,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ),
                new LookLibraryAndPickControllerEffect(
                        4, 1,
                        StaticFilters.FILTER_CARD_CREATURE_OR_LAND,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), YouGainedLifeCondition.getZero(), "look at the top four cards of your library. " +
                "You may reveal a creature or land card from among them and put it into your hand. " +
                "If you gained life this turn, you may instead reveal two creature and/or land cards from among " +
                "them and put them into your hand. Put the rest on the bottom of your library in a random order"
        ));
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
        this.getSpellAbility().setAbilityWord(AbilityWord.INFUSION);
    }

    private FollowTheLumarets(final FollowTheLumarets card) {
        super(card);
    }

    @Override
    public FollowTheLumarets copy() {
        return new FollowTheLumarets(this);
    }
}
