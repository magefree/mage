package mage.cards.e;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnduringTenacity extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public EnduringTenacity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you gain life, target opponent loses that much life.
        Ability ability = new GainLifeControllerTriggeredAbility(new LoseLifeTargetEffect(SavedGainedLifeValue.MUCH));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Enduring Tenacity dies, if it was a creature, return it to the battlefield under its owner's control. It's an enchantment.
        this.addAbility(new ConditionalTriggeredAbility(
                new DiesSourceTriggeredAbility(new EnduringTenacityReturnEffect()),
                condition, "When {this} dies, if it was a creature, " +
                "return it to the battlefield under its owner's control. It's an enchantment."
        ));
    }

    private EnduringTenacity(final EnduringTenacity card) {
        super(card);
    }

    @Override
    public EnduringTenacity copy() {
        return new EnduringTenacity(this);
    }
}

class EnduringTenacityReturnEffect extends OneShotEffect {

    EnduringTenacityReturnEffect() {
        super(Outcome.Benefit);
    }

    private EnduringTenacityReturnEffect(final EnduringTenacityReturnEffect effect) {
        super(effect);
    }

    @Override
    public EnduringTenacityReturnEffect copy() {
        return new EnduringTenacityReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null
                || card.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter() + 1) {
            return false;
        }
        game.addEffect(new EnduringTenacityTypeEffect()
                .setTargetPointer(new FixedTarget(new MageObjectReference(card, game, 1))), source);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

class EnduringTenacityTypeEffect extends ContinuousEffectImpl {

    EnduringTenacityTypeEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    private EnduringTenacityTypeEffect(final EnduringTenacityTypeEffect effect) {
        super(effect);
    }

    @Override
    public EnduringTenacityTypeEffect copy() {
        return new EnduringTenacityTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.retainAllEnchantmentSubTypes(game);
        permanent.removeAllCardTypes(game);
        permanent.addCardType(game, CardType.ENCHANTMENT);
        return true;
    }
}
