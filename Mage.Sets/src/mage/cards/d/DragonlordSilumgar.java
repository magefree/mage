package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.SourceHasRemainedInSameZoneCondition;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.watchers.common.LostControlWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class DragonlordSilumgar extends CardImpl {

    public DragonlordSilumgar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Dragonlord Silumgar enters the battlefield, gain control of target creature or planeswalker for as long as you control Dragonlord Silumgar.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DragonlordSilumgarEffect(), false);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);

    }

    private DragonlordSilumgar(final DragonlordSilumgar card) {
        super(card);
    }

    @Override
    public DragonlordSilumgar copy() {
        return new DragonlordSilumgar(this);
    }
}

class DragonlordSilumgarEffect extends OneShotEffect {

    public DragonlordSilumgarEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of target creature or planeswalker for as long as you control {this}";
    }

    public DragonlordSilumgarEffect(final DragonlordSilumgarEffect effect) {
        super(effect);
    }

    @Override
    public DragonlordSilumgarEffect copy() {
        return new DragonlordSilumgarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && sourcePermanent != null) {
            if (target != null && controller.getId().equals(sourcePermanent.getControllerId())) {
                SourceHasRemainedInSameZoneCondition condition = new SourceHasRemainedInSameZoneCondition(sourcePermanent.getId());

                game.addEffect(new ConditionalContinuousEffect(
                        new GainControlTargetEffect(Duration.Custom),
                        new CompoundCondition(new SourceOnBattlefieldControlUnchangedCondition(), condition),
                        null),
                        source);
                if (!game.isSimulation()) {
                    game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " gained control of " + target.getLogName());
                }
                sourcePermanent.addInfo("gained control of", CardUtil.addToolTipMarkTags("Gained control of: " + GameLog.getColoredObjectIdNameForTooltip(target)), game);
            }
            return true;
        }
        return false;
    }
}
