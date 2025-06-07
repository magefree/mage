package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.delayed.AddCounterNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LongListOfTheEnts extends CardImpl {

    public LongListOfTheEnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after VI.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_VI);

        // I, II, III, IV, V, VI -- Note a creature type that hasn't been noted for Long List of the Ents. When you cast your next creature spell of that type this turn, that creature enters the battlefield with an additional +1/+1 counter on it.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_VI, new LongListOfTheEntsEffect()
        );

        this.addAbility(sagaAbility.addHint(LongListOfTheEntsHint.instance));
    }

    private LongListOfTheEnts(final LongListOfTheEnts card) {
        super(card);
    }

    @Override
    public LongListOfTheEnts copy() {
        return new LongListOfTheEnts(this);
    }

    static String getKey(Game game, Ability source) {
        return "EntList_" + source.getSourceId() + "_" + CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
    }

}

enum LongListOfTheEntsHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (ability.getSourcePermanentIfItStillExists(game) == null) {
            return null;
        }
        Set<SubType> subTypes = (Set<SubType>) game.getState().getValue(LongListOfTheEnts.getKey(game, ability));
        if (subTypes == null || subTypes.isEmpty()) {
            return "No creature types have been noted yet.";
        }
        return subTypes
                .stream()
                .map(SubType::toString)
                .collect(Collectors.joining(
                        ", ", "Noted creature types: " + subTypes.size() + " (", ")"
                ));
    }

    @Override
    public LongListOfTheEntsHint copy() {
        return this;
    }
}

class LongListOfTheEntsEffect extends OneShotEffect {

    LongListOfTheEntsEffect() {
        super(Outcome.Benefit);
        staticText = "note a creature type that hasn't been noted for {this}. When you next cast a creature spell " +
                "of that type this turn, that creature enters the battlefield with an additional +1/+1 counter on it.";
    }

    private LongListOfTheEntsEffect(final LongListOfTheEntsEffect effect) {
        super(effect);
    }

    @Override
    public LongListOfTheEntsEffect copy() {
        return new LongListOfTheEntsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Object existingEntList = game.getState().getValue(LongListOfTheEnts.getKey(game, source));
        Set<SubType> newEntList;
        if (existingEntList == null) {
            newEntList = new LinkedHashSet<>();
        } else {
            newEntList = new LinkedHashSet<>((Set<SubType>) existingEntList);
        }
        Set<String> chosenTypes = newEntList
                .stream()
                .map(SubType::toString)
                .collect(Collectors.toSet());

        ChoiceCreatureType choice = new ChoiceCreatureType(game, source);
        choice.getKeyChoices().keySet().removeIf(chosenTypes::contains);
        if (!player.choose(Outcome.BoostCreature, choice, game)) {
            return false;
        }
        SubType subType = SubType.byDescription(choice.getChoiceKey());
        game.informPlayers(player.getLogName() + " notes the creature type " + subType);
        newEntList.add(subType);
        game.getState().setValue(LongListOfTheEnts.getKey(game, source), newEntList);
        FilterSpell filter = new FilterCreatureSpell("a creature spell of that type");
        filter.add(subType.getPredicate());
        game.addDelayedTriggeredAbility(new AddCounterNextSpellDelayedTriggeredAbility(filter), source);
        return true;
    }
}
