package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceOnBattlefieldCondition;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class InfernalDenizen extends CardImpl {

    public InfernalDenizen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, sacrifice two Swamps. If you can't, tap Infernal Denizen, and an opponent may gain control of a creature you control of their choice for as long as Infernal Denizen remains on the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new InfernalDenizenEffect(), TargetController.YOU, false));

        // {tap}: Gain control of target creature for as long as Infernal Denizen remains on the battlefield.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom, true),
                new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                "gain control of target creature for as long as {this} remains on the battlefield");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private InfernalDenizen(final InfernalDenizen card) {
        super(card);
    }

    @Override
    public InfernalDenizen copy() {
        return new InfernalDenizen(this);
    }
}

class InfernalDenizenEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    InfernalDenizenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice two Swamps. If you can't, tap {this}, "
                + "and an opponent may gain control of a creature you control of their choice "
                + "for as long as {this} remains on the battlefield";
    }

    InfernalDenizenEffect(final InfernalDenizenEffect effect) {
        super(effect);
    }

    @Override
    public InfernalDenizenEffect copy() {
        return new InfernalDenizenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            DynamicValue swamps = new PermanentsOnBattlefieldCount(filter);
            boolean canSac = swamps.calculate(game, source, this) > 1;
            Effect effect = new SacrificeControllerEffect(filter, 2, "Sacrifice two Swamps");
            effect.apply(game, source);
            if (!canSac) {
                if (creature != null) {
                    creature.tap(source, game);
                }
                TargetOpponent targetOpp = new TargetOpponent(true);
                if (targetOpp.canChoose(source.getSourceId(), player.getId(), game)
                        && targetOpp.choose(Outcome.Detriment, player.getId(), source.getSourceId(), game)) {
                    Player opponent = game.getPlayer(targetOpp.getFirstTarget());
                    if (opponent != null) {
                        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature controlled by " + player.getLogName());
                        filter2.add(new ControllerIdPredicate(player.getId()));
                        TargetCreaturePermanent targetCreature = new TargetCreaturePermanent(1, 1, filter2, true);
                        targetCreature.setTargetController(opponent.getId());
                        if (targetCreature.canChoose(source.getSourceId(), id, game)
                                && opponent.chooseUse(Outcome.GainControl, "Gain control of a creature?", source, game)
                                && opponent.chooseTarget(Outcome.GainControl, targetCreature, source, game)) {
                            ConditionalContinuousEffect giveEffect = new ConditionalContinuousEffect(
                                    new GainControlTargetEffect(Duration.Custom, true, opponent.getId()),
                                    SourceOnBattlefieldCondition.instance,
                                    "");
                            giveEffect.setTargetPointer(new FixedTarget(targetCreature.getFirstTarget(), game));
                            game.addEffect(giveEffect, source);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
