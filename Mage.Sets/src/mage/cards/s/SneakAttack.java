package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SneakAttack extends CardImpl {

    public SneakAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // {R}: You may put a creature card from your hand onto the battlefield. That creature gains haste. Sacrifice the creature at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new SneakAttackEffect(), new ManaCostsImpl("{R}")));
    }

    private SneakAttack(final SneakAttack card) {
        super(card);
    }

    @Override
    public SneakAttack copy() {
        return new SneakAttack(this);
    }
}

class SneakAttackEffect extends OneShotEffect {

    private static final String choiceText = "Put a creature card from your hand onto the battlefield?";

    SneakAttackEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put a creature card from your hand onto the battlefield. " +
                "That creature gains haste. Sacrifice the creature at the beginning of the next end step";
    }

    private SneakAttackEffect(final SneakAttackEffect effect) {
        super(effect);
    }

    @Override
    public SneakAttackEffect copy() {
        return new SneakAttackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
            return true;
        }
        TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE);
        if (!controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
            return true;
        }
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(CardUtil.getDefaultCardSideForBattlefield(game, card).getId());
        if (permanent == null) {
            return true;
        }
        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effect, source);

        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect(
                        "sacrifice " + card.getName(),
                        source.getControllerId()
                ).setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
