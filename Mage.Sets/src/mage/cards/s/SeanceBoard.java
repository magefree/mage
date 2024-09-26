package mage.cards.s;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Cguy7777
 */
public final class SeanceBoard extends CardImpl {

    public SeanceBoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Morbid -- At the beginning of each end step, if a creature died this turn, put a soul counter on Seance Board.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.SOUL.createInstance()),
                TargetController.ANY,
                MorbidCondition.instance,
                false
        ).addHint(MorbidHint.instance).setAbilityWord(AbilityWord.MORBID));

        // {T}: Add X mana of any one color, where X is the number of soul counters on Seance Board.
        // Spend this mana only to cast instant, sorcery, Demon, and Spirit spells.
        this.addAbility(new SimpleManaAbility(new SeanceBoardManaEffect(), new TapSourceCost()));
    }

    private SeanceBoard(final SeanceBoard card) {
        super(card);
    }

    @Override
    public SeanceBoard copy() {
        return new SeanceBoard(this);
    }
}

class SeanceBoardManaEffect extends ManaEffect {

    private static final FilterSpell filter = new FilterSpell("instant, sorcery, Demon, and Spirit spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.DEMON.getPredicate(),
                SubType.SPIRIT.getPredicate()));
    }

    private final ConditionalManaBuilder manaBuilder
            = new ConditionalSpellManaBuilder(filter);

    SeanceBoardManaEffect() {
        this.staticText = "Add X mana of any one color, where X is the number of soul counters on {this}. "
                + manaBuilder.getRule();
    }

    private SeanceBoardManaEffect(final SeanceBoardManaEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent != null) {
            int soulCounters = permanent.getCounters(game).getCount(CounterType.SOUL);
            netMana.add(manaBuilder.setMana(Mana.AnyMana(soulCounters), source, game).build());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }

        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (controller == null || permanent == null) {
            return mana;
        }

        ChoiceColor choice = new ChoiceColor();
        if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
            return mana;
        }
        Mana chosen = choice.getMana(permanent.getCounters(game).getCount(CounterType.SOUL));
        return manaBuilder.setMana(chosen, source, game).build();
    }

    @Override
    public SeanceBoardManaEffect copy() {
        return new SeanceBoardManaEffect(this);
    }
}
