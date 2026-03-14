package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NinjaTeen extends CardImpl {

    public NinjaTeen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // Whenever a creature you control leaves the battlefield, each opponent loses 1 life.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new LoseLifeOpponentsEffect(1), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        // {1}{B}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{1}{B}"));

        // Creatures you control get +1/+0 and have menace.
        Ability ability = new SimpleStaticAbility(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)
        );
        ability.addEffect(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("and have menace"));
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(ability, 2)));

        // {B}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{B}"));

        // Creature cards in your graveyard have sneak {3}{B}.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new NinjaTeenAbilityEffect(), 3)));

        // You may cast creature spells from your graveyard using their sneak abilities.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new NinjaTeenCastEffect(), 3)));
    }

    private NinjaTeen(final NinjaTeen card) {
        super(card);
    }

    @Override
    public NinjaTeen copy() {
        return new NinjaTeen(this);
    }
}

class NinjaTeenAbilityEffect extends ContinuousEffectImpl {

    NinjaTeenAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "creature cards in your graveyard have sneak {3}{B}";
    }

    private NinjaTeenAbilityEffect(final NinjaTeenAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Card card : controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            Ability ability = new SneakAbility(card, "{3}{B}");
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public NinjaTeenAbilityEffect copy() {
        return new NinjaTeenAbilityEffect(this);
    }
}

class NinjaTeenCastEffect extends AsThoughEffectImpl {

    NinjaTeenCastEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PutCreatureInPlay);
        staticText = "you may cast creature spells from your graveyard using their sneak abilities";
    }

    private NinjaTeenCastEffect(final NinjaTeenCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NinjaTeenCastEffect copy() {
        return new NinjaTeenCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (!source.isControlledBy(playerId)
                || !source.isControlledBy(game.getOwnerId(objectId))
                || game.getState().getZone(objectId) != Zone.GRAVEYARD
                || !(affectedAbility instanceof SneakAbility)) {
            return false;
        }
        Card card = game.getCard(objectId);
        return card != null && card.isCreature(game);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
