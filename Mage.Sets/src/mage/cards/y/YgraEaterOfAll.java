package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class YgraEaterOfAll extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.FOOD, "Food");

    public YgraEaterOfAll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL, SubType.CAT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Ward—Sacrifice a Food.
        this.addAbility(new WardAbility(new SacrificeTargetCost(filter), false));

        // Other creatures are Food artifacts in addition to their other types and have "{2}, {T}, Sacrifice this permanent: You gain 3 life."
        this.addAbility(new SimpleStaticAbility(new YgraEaterOfAllEffect()));

        // Whenever a Food is put into a graveyard from the battlefield, put two +1/+1 counters on Ygra, Eater of All.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), filter,
                "Whenever a Food is put into a graveyard from the battlefield, ", false
        ));
    }

    private YgraEaterOfAll(final YgraEaterOfAll card) {
        super(card);
    }

    @Override
    public YgraEaterOfAll copy() {
        return new YgraEaterOfAll(this);
    }
}

class YgraEaterOfAllEffect extends ContinuousEffectImpl {

    YgraEaterOfAllEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AddAbility);
        staticText = "Other creatures are Food artifacts in addition to their other types " +
                "and have \"{2}, {T}, Sacrifice this permanent: You gain 3 life.\"";
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
        this.dependendToTypes.add(DependencyType.BecomeCreature);
    }

    private YgraEaterOfAllEffect(final YgraEaterOfAllEffect effect) {
        super(effect);
    }

    @Override
    public YgraEaterOfAllEffect copy() {
        return new YgraEaterOfAllEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getControllerId(), source, game)) {
            if (permanent.getId().equals(source.getSourceId())) {
                continue;
            }
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addCardType(game, CardType.ARTIFACT);
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
