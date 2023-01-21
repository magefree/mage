package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianBeastToxicToken;
import mage.game.permanent.token.PhyrexianGolemToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class GoliathHatchery extends CardImpl {

    public GoliathHatchery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // When Goliath Hatchery enters the battlefield, create two 3/3 green Phyrexian Beast creature tokens with toxic 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new PhyrexianBeastToxicToken(), 2)
        ));

        // Corrupted -- At the beginning of your upkeep, if an opponent has three or more poison counters, choose a creature you control, then draw cards equal to its total toxic value.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        new GoliathHatcheryEffect(), TargetController.YOU, false
                ), CorruptedCondition.instance, null
        ));
    }

    private GoliathHatchery(final GoliathHatchery card) {
        super(card);
    }

    @Override
    public GoliathHatchery copy() {
        return new GoliathHatchery(this);
    }
}

class GoliathHatcheryEffect extends OneShotEffect {

    GoliathHatcheryEffect() {
        super(Outcome.Benefit);
        staticText = "if an opponent has three or more poison counters, " +
                "choose a creature you control, then draw cards equal to its total toxic value";
    }

    private GoliathHatcheryEffect(final GoliathHatcheryEffect effect) {
        super(effect);
    }

    @Override
    public GoliathHatcheryEffect copy() {
        return new GoliathHatcheryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int amount = CardUtil.castStream(
                permanent.getAbilities(game).stream(), ToxicAbility.class
        ).mapToInt(ToxicAbility::getAmount).sum();
        return amount > 0 && player.drawCards(amount, source, game) > 0;
    }
}
