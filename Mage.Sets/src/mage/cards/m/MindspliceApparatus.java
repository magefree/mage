package mage.cards.m;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class MindspliceApparatus extends CardImpl {

    public MindspliceApparatus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // At the beginning of your upkeep, put an oil counter on Mindsplice Apparatus.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()), TargetController.YOU, false
        ));

        // Instant and sorcery spells you cast cost {1} less to cast for each oil counter on Mindsplice Apparatus.
        this.addAbility(new SimpleStaticAbility(new MindspliceApparatusEffect()));
    }

    private MindspliceApparatus(final MindspliceApparatus card) {
        super(card);
    }

    @Override
    public MindspliceApparatus copy() {
        return new MindspliceApparatus(this);
    }
}

class MindspliceApparatusEffect extends CostModificationEffectImpl {

    MindspliceApparatusEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "instant and sorcery spells you cast cost {1} less to cast for each oil counter on {this}";
    }

    MindspliceApparatusEffect(MindspliceApparatusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int amount = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(CounterType.OIL))
                .orElse(0);
        if (amount < 1) {
            return false;
        }
        CardUtil.adjustCost((SpellAbility) abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        Card sourceCard = game.getCard(abilityToModify.getSourceId());
        return sourceCard != null
                && abilityToModify.isControlledBy(source.getControllerId())
                && sourceCard.isInstantOrSorcery(game);
    }

    @Override
    public MindspliceApparatusEffect copy() {
        return new MindspliceApparatusEffect(this);
    }
}
