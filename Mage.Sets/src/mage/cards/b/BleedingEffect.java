package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BleedingEffect extends CardImpl {

    public BleedingEffect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // At the beginning of combat on your turn, creatures you control gain flying until end of turn if a creature card in your graveyard has flying.
        // The same is true for first strike, double strike, deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BleedingEffectEffect()));
    }

    private BleedingEffect(final BleedingEffect card) {
        super(card);
    }

    @Override
    public BleedingEffect copy() {
        return new BleedingEffect(this);
    }
}

class BleedingEffectEffect extends OneShotEffect {

    private static final List<Class<? extends Ability>> abilities = Arrays.asList(
            FlyingAbility.class,
            FirstStrikeAbility.class,
            DoubleStrikeAbility.class,
            DeathtouchAbility.class,
            HexproofBaseAbility.class,
            IndestructibleAbility.class,
            LifelinkAbility.class,
            MenaceAbility.class,
            ReachAbility.class,
            TrampleAbility.class,
            ReachAbility.class,
            TrampleAbility.class,
            VigilanceAbility.class
    );

    BleedingEffectEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "creatures you control gain flying until end of turn if a creature card in your graveyard has flying." +
                " The same is true for first strike, double strike, deathtouch, hexproof, indestructible, lifelink, menace, reach, trample, and vigilance.";
    }

    private BleedingEffectEffect(final BleedingEffectEffect effect) {
        super(effect);
    }

    @Override
    public BleedingEffectEffect copy() {
        return new BleedingEffectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Ability menaceAbility = new MenaceAbility(false);
        controller.getGraveyard().stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .flatMap(c -> c.getAbilities(game).stream())
                .filter(a -> abilities.stream().anyMatch(x -> x.isInstance(a)))
                .map(a -> a instanceof MenaceAbility ? menaceAbility : a.copy()) // since menace is not a singleton
                .distinct() // all others MageSingleton so this works to eliminate redundancy
                .forEach(a -> game.addEffect(new GainAbilityControlledEffect(
                        a,
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES), source));
        return true;
    }
}
