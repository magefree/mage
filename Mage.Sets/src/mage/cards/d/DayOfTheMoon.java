package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DayOfTheMoon extends CardImpl {

    public DayOfTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II, III -- Choose a creature card name, then goad all creatures with a name chosen for Day of the Moon.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III, new DayOfTheMoonEffect());
        this.addAbility(sagaAbility);
    }

    private DayOfTheMoon(final DayOfTheMoon card) {
        super(card);
    }

    @Override
    public DayOfTheMoon copy() {
        return new DayOfTheMoon(this);
    }
}

class DayOfTheMoonEffect extends OneShotEffect {

    DayOfTheMoonEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature card name, then goad all creatures with a name chosen for {this}";
    }

    private DayOfTheMoonEffect(final DayOfTheMoonEffect effect) {
        super(effect);
    }

    @Override
    public DayOfTheMoonEffect copy() {
        return new DayOfTheMoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice cardChoice = ChooseACardNameEffect.TypeOfName.CREATURE_NAME.makeChoiceObject();
        player.choose(outcome, cardChoice, game);
        String cardName = cardChoice.getChoice();
        List<String> names = getOrSetValue(game, source);
        names.add(cardName);
        names.removeIf(Objects::isNull);
        if (names.isEmpty()) {
            return true;
        }
        game.informPlayers(
                CardUtil.getSourceLogName(game, source) + ": " + player.getName() + ", chosen name: [" + cardName + ']'
        );
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.addInfo(
                        "NAMED_CARD", CardUtil.addToolTipMarkTags(
                                "Chosen names: " + CardUtil.concatWithAnd(names)
                        ), game
                ));
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.or(names.stream().map(NamePredicate::new).collect(Collectors.toSet())));
        game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTargets(
                game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game), game
        )), source);
        return true;
    }

    private static List<String> getOrSetValue(Game game, Ability source) {
        String key = "DayOfTheMoon_" + source.getControllerId() + '_' + source.getStackMomentSourceZCC();
        List<String> list = (List<String>) game.getState().getValue(key);
        if (list != null) {
            return list;
        }
        return game.getState().setValue(key, new ArrayList<>());
    }
}
