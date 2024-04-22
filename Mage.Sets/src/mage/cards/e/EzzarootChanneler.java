package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EzzarootChanneler extends CardImpl {

    public EzzarootChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Creature spells you cast cost {X} less to cast, where X is the amount of life you gained this turn.
        this.addAbility(
                new SimpleStaticAbility(new EzzarootChannelerEffect())
                        .addHint(ControllerGainedLifeCount.getHint()),
                new PlayerGainedLifeWatcher()
        );

        // {T}: You gain 2 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(2), new TapSourceCost()));
    }

    private EzzarootChanneler(final EzzarootChanneler card) {
        super(card);
    }

    @Override
    public EzzarootChanneler copy() {
        return new EzzarootChanneler(this);
    }
}

class EzzarootChannelerEffect extends CostModificationEffectImpl {

    EzzarootChannelerEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "creature spells you cast cost {X} less to cast, " +
                "where X is the amount of life you gained this turn";
    }

    private EzzarootChannelerEffect(final EzzarootChannelerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, Math.max(0, ControllerGainedLifeCount.instance.calculate(game, source, this)));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && ((SpellAbility) abilityToModify).getCharacteristics(game).isCreature(game)
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public EzzarootChannelerEffect copy() {
        return new EzzarootChannelerEffect(this);
    }
}
