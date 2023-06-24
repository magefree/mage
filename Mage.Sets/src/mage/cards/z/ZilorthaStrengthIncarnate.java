package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class ZilorthaStrengthIncarnate extends CardImpl {

    public ZilorthaStrengthIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ZilorthaStrengthIncarnateEffect()));
    }

    private ZilorthaStrengthIncarnate(ZilorthaStrengthIncarnate card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new ZilorthaStrengthIncarnate(this);
    }
}

class ZilorthaStrengthIncarnateEffect extends ContinuousEffectImpl {

    ZilorthaStrengthIncarnateEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Lethal damage dealt to creatures you control is determined by their power rather than their toughness";
    }

    private ZilorthaStrengthIncarnateEffect(ZilorthaStrengthIncarnateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // Change the rule
        FilterCreaturePermanent filter = StaticFilters.FILTER_PERMANENT_CREATURE.copy();
        filter.add(new ControllerIdPredicate(source.getControllerId()));
        game.getState().addPowerInsteadOfToughnessForDamageLethalityFilter(source.getSourceId(), filter);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

    @Override
    public ContinuousEffect copy() {
        return new ZilorthaStrengthIncarnateEffect(this);
    }
}