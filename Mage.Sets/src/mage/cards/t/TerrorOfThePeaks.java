package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerrorOfThePeaks extends CardImpl {

    public TerrorOfThePeaks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast that target Terror of the Peaks cost an additional 3 life to cast.
        this.addAbility(new SimpleStaticAbility(new TerrorOfThePeaksCostIncreaseEffect()));

        // Whenever another creature enters the battlefield under your control, Terror of the Peaks deals damage equal to that creature's power to any target.
        this.addAbility(new TerrorOfThePeaksTriggeredAbility());
    }

    private TerrorOfThePeaks(final TerrorOfThePeaks card) {
        super(card);
    }

    @Override
    public TerrorOfThePeaks copy() {
        return new TerrorOfThePeaks(this);
    }
}

class TerrorOfThePeaksCostIncreaseEffect extends CostModificationEffectImpl {

    TerrorOfThePeaksCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost an additional 3 life to cast";
    }

    private TerrorOfThePeaksCostIncreaseEffect(TerrorOfThePeaksCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        spellAbility.addCost(new PayLifeCost(3));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
            return false;
        }
        return abilityToModify
                .getModes()
                .getSelectedModes()
                .stream()
                .map(uuid -> abilityToModify.getModes().get(uuid))
                .map(Mode::getTargets)
                .flatMap(Collection::stream)
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(uuid -> uuid.equals(source.getSourceId()));
    }

    @Override
    public TerrorOfThePeaksCostIncreaseEffect copy() {
        return new TerrorOfThePeaksCostIncreaseEffect(this);
    }
}

class TerrorOfThePeaksTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    TerrorOfThePeaksTriggeredAbility() {
        super(null, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE);
    }

    private TerrorOfThePeaksTriggeredAbility(final TerrorOfThePeaksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DamageTargetEffect(permanent.getPower().getValue()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield under your control, " +
                "{this} deals damage equal to that creature's power to any target.";
    }
}
