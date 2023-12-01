package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnowonTheRuinThief extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ROGUE, "Rogues");

    public AnowonTheRuinThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Rogues you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));

        // Whenever one or more Rogues you control deal combat damage to a player, that player mills a card for each 1 damage dealt to them. If the player mills at least one creature card this way, you draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(Zone.BATTLEFIELD, new AnowonTheRuinThiefEffect(),
                filter, SetTargetPointer.PLAYER, false));
    }

    private AnowonTheRuinThief(final AnowonTheRuinThief card) {
        super(card);
    }

    @Override
    public AnowonTheRuinThief copy() {
        return new AnowonTheRuinThief(this);
    }
}

class AnowonTheRuinThiefEffect extends OneShotEffect {

    AnowonTheRuinThiefEffect() {
        super(Outcome.Benefit);
        staticText = "that player mills a card for each 1 damage dealt to them. " +
                "If the player mills at least one creature card this way, you draw a card.";
    }

    private AnowonTheRuinThiefEffect(final AnowonTheRuinThiefEffect effect) {
        super(effect);
    }

    @Override
    public AnowonTheRuinThiefEffect copy() {
        return new AnowonTheRuinThiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        int damage = SavedDamageValue.MANY.calculate(game, source, this);
        if (player.millCards(damage, source, game).count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }
}
