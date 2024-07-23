package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtStalkerWarlock extends CardImpl {

    public ThoughtStalkerWarlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Thought-Stalker Warlock enters, choose target opponent. If they lost life this turn, they reveal their hand, you choose a nonland card from it, and they discard that card. Otherwise, they discard a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND),
                new DiscardTargetEffect(1), ThoughtStalkerWarlockCondition.instance, "choose " +
                "target opponent. If they lost life this turn, they reveal their hand, you choose " +
                "a nonland card from it, and they discard that card. Otherwise, they discard a card"
        ));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThoughtStalkerWarlock(final ThoughtStalkerWarlock card) {
        super(card);
    }

    @Override
    public ThoughtStalkerWarlock copy() {
        return new ThoughtStalkerWarlock(this);
    }
}

enum ThoughtStalkerWarlockCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(source.getFirstTarget()) > 0;
    }
}