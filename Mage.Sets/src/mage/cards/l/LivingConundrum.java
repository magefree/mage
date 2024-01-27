package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Library;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivingConundrum extends CardImpl {

    public LivingConundrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // If you would draw a card while your library has no cards in it, skip that draw instead.
        this.addAbility(new SimpleStaticAbility(new LivingConundrumDrawEffect()));

        // As long as there are no cards in your library, Living Conundrum has base power and toughness 10/10 and has flying and vigilance.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new SetBasePowerToughnessSourceEffect(10, 10, Duration.WhileOnBattlefield),
                LivingConundrumCondition.instance, "as long as there are no cards in your library, " +
                "{this} has base power and toughness 10/10"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                LivingConundrumCondition.instance, "and has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
                LivingConundrumCondition.instance, "and vigilance"
        ));
        this.addAbility(ability);
    }

    private LivingConundrum(final LivingConundrum card) {
        super(card);
    }

    @Override
    public LivingConundrum copy() {
        return new LivingConundrum(this);
    }
}

class LivingConundrumDrawEffect extends ReplacementEffectImpl {

    LivingConundrumDrawEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you would draw a card while your library has no cards in it, skip that draw instead";
    }

    private LivingConundrumDrawEffect(final LivingConundrumDrawEffect effect) {
        super(effect);
    }

    @Override
    public LivingConundrumDrawEffect copy() {
        return new LivingConundrumDrawEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Player player = game.getPlayer(event.getPlayerId());
        return player != null && !player.getLibrary().hasCards();
    }
}

enum LivingConundrumCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .map(Player::getLibrary)
                .map(Library::size)
                .equals(0);
    }
}
