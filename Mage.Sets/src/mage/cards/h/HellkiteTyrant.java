
package mage.cards.h;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class HellkiteTyrant extends CardImpl {

    public HellkiteTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Hellkite Tyrant deals combat damage to a player, gain control of all artifacts that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellkiteTyrantEffect(), false, true));

        // At the beginning of your upkeep, if you control twenty or more artifacts, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                new PermanentsOnTheBattlefieldCondition(new FilterArtifactPermanent(), ComparisonType.MORE_THAN, 19),
                "At the beginning of your upkeep, if you control twenty or more artifacts, you win the game."
        ).addHint(new ValueHint("Artifacts you control", new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT))));
    }

    private HellkiteTyrant(final HellkiteTyrant card) {
        super(card);
    }

    @Override
    public HellkiteTyrant copy() {
        return new HellkiteTyrant(this);
    }
}

class HellkiteTyrantEffect extends OneShotEffect {

    public HellkiteTyrantEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of all artifacts that player controls";
    }

    private HellkiteTyrantEffect(final HellkiteTyrantEffect effect) {
        super(effect);
    }

    @Override
    public HellkiteTyrantEffect copy() {
        return new HellkiteTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactPermanent();
        filter.add(new ControllerIdPredicate(player.getId()));

        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            ContinuousEffect effect = new HellkiteTyrantControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class HellkiteTyrantControlEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public HellkiteTyrantControlEffect(UUID controllerId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    private HellkiteTyrantControlEffect(final HellkiteTyrantControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public HellkiteTyrantControlEffect copy() {
        return new HellkiteTyrantControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game, source);
        }
        // Permanent is no longer on the battlefield, the effect can be discarded.
        discard();
        return false;
    }
}
