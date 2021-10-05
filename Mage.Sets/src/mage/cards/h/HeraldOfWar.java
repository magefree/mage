package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public final class HeraldOfWar extends CardImpl {

    public HeraldOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Herald of War attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"), false));

        // Angel spells and Human spells you cast cost {1} less to cast for each +1/+1 counter on Herald of War.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeraldOfWarCostReductionEffect()));
    }

    private HeraldOfWar(final HeraldOfWar card) {
        super(card);
    }

    @Override
    public HeraldOfWar copy() {
        return new HeraldOfWar(this);
    }
}

class HeraldOfWarCostReductionEffect extends CostModificationEffectImpl {

    HeraldOfWarCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Angel spells and Human spells you cast cost {1} less to cast for each +1/+1 counter on {this}";
    }

    HeraldOfWarCostReductionEffect(HeraldOfWarCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int amount = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (amount > 0) {
                CardUtil.adjustCost(spellAbility, amount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card sourceCard = game.getCard(abilityToModify.getSourceId());
            if (sourceCard != null && abilityToModify.isControlledBy(source.getControllerId()) && (sourceCard.hasSubtype(SubType.ANGEL, game) || sourceCard.hasSubtype(SubType.HUMAN, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HeraldOfWarCostReductionEffect copy() {
        return new HeraldOfWarCostReductionEffect(this);
    }

}
