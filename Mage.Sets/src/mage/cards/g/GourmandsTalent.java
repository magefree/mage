package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.token.FoodAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RaccoonToken;

/**
 *
 * @author Grath
 */
public final class GourmandsTalent extends CardImpl {

    public GourmandsTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        
        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // During your turn, artifacts you control are Foods in addition to their other types and have "{2}, {T}, Sacrifice this artifact: You gain 3 life."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GourmandsTalentEffect(), MyTurnCondition.instance, "During your turn, artifacts you control " +
                "are Foods in addition to their other types and have \"{2}, {T}, Sacrifice this artifact: You gain 3 " +
                "life.\""
        )));

        // {2}{G}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{2}{G}"));
        // Whenever you gain life for the first time each turn, create a 3/3 green Raccoon creature token.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new GainLifeFirstTimeTriggeredAbility(new CreateTokenEffect(new RaccoonToken())), 2)));

        // {3}{G}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{3}{G}"));

        // Whenever you gain life for the first time each turn, put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new GainLifeFirstTimeTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)), 3)));
    }

    private GourmandsTalent(final GourmandsTalent card) {
        super(card);
    }

    @Override
    public GourmandsTalent copy() {
        return new GourmandsTalent(this);
    }
}

class GourmandsTalentEffect extends ContinuousEffectImpl {

    GourmandsTalentEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AddAbility);
        staticText = "Other creatures are Food artifacts in addition to their other types " +
                "and have \"{2}, {T}, Sacrifice this permanent: You gain 3 life.\"";
        this.dependencyTypes.add(DependencyType.AddingAbility);
    }

    private GourmandsTalentEffect(final GourmandsTalentEffect effect) {
        super(effect);
    }

    @Override
    public GourmandsTalentEffect copy() {
        return new GourmandsTalentEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, source.getControllerId(), source, game)) {
            if (permanent.getId().equals(source.getSourceId())) {
                continue;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.FOOD);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(new FoodAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
