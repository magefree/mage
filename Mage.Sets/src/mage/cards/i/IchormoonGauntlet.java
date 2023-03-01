package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class IchormoonGauntlet extends CardImpl {

    public IchormoonGauntlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // Planeswalkers you control have "[0]: Proliferate" and "[-12]: Take an extra turn after this one."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                new LoyaltyAbility(new ProliferateEffect(), 0),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"0: Proliferate\""));
        ability.addEffect(new GainAbilityControlledEffect(
                new LoyaltyAbility(new AddExtraTurnControllerEffect(), -12),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("and \"-12: Take an extra turn after this one.\""));
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, choose a counter on target permanent. Put an additional counter of that kind on that permanent.
        ability = new SpellCastControllerTriggeredAbility(
                new IchormoonGauntletEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private IchormoonGauntlet(final IchormoonGauntlet card) {
        super(card);
    }

    @Override
    public IchormoonGauntlet copy() {
        return new IchormoonGauntlet(this);
    }
}

class IchormoonGauntletEffect extends OneShotEffect {

    IchormoonGauntletEffect() {
        super(Outcome.Benefit);
        staticText = "choose a counter on target permanent. Put an additional counter of that kind on that permanent";
    }

    IchormoonGauntletEffect(final IchormoonGauntletEffect effect) {
        super(effect);
    }

    @Override
    public IchormoonGauntletEffect copy() {
        return new IchormoonGauntletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent == null || permanent.getCounters(game).isEmpty()) {
            return false;
        }
        if (permanent.getCounters(game).size() == 1) {
            for (Counter counter : permanent.getCounters(game).values()) {
                Counter newCounter = new Counter(counter.getName());
                permanent.addCounters(newCounter, source.getControllerId(), source, game);
            }
        } else {
            Choice choice = new ChoiceImpl(true);
            Set<String> choices = new HashSet<>(permanent.getCounters(game).size());
            for (Counter counter : permanent.getCounters(game).values()) {
                choices.add(counter.getName());
            }
            choice.setChoices(choices);
            choice.setMessage("Choose a counter");
            if (controller.choose(Outcome.Benefit, choice, game)) {
                for (Counter counter : permanent.getCounters(game).values()) {
                    if (counter.getName().equals(choice.getChoice())) {
                        Counter newCounter = new Counter(counter.getName());
                        permanent.addCounters(newCounter, source.getControllerId(), source, game);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
