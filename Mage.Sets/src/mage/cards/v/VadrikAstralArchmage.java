package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VadrikAstralArchmage extends CardImpl {

    public VadrikAstralArchmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // If it's neither day nor night, it becomes day as Vadrik, Astral Archmage enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Instant and sorcery spells you cast cost {X} less to cast, where X is Vadrik's power.
        this.addAbility(new SimpleStaticAbility(new VadrikAstralArchmageEffect()));

        // Whenever day becomes night or night becomes day, put a +1/+1 counter on Vadrik.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private VadrikAstralArchmage(final VadrikAstralArchmage card) {
        super(card);
    }

    @Override
    public VadrikAstralArchmage copy() {
        return new VadrikAstralArchmage(this);
    }
}

class VadrikAstralArchmageEffect extends CostModificationEffectImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    VadrikAstralArchmageEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "instant and sorcery spells you cast cost {X} less to cast, where X is {this}'s power";
    }

    private VadrikAstralArchmageEffect(final VadrikAstralArchmageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, xValue.calculate(game, source, this));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && ((SpellAbility) abilityToModify).getCharacteristics(game).isInstantOrSorcery(game)
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public VadrikAstralArchmageEffect copy() {
        return new VadrikAstralArchmageEffect(this);
    }
}
