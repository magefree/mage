package mage.cards.t;

import mage.MageInt;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FungusDinosaurToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class TheSkullsporeNexus extends CardImpl {

    public TheSkullsporeNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // This spell costs {X} less to cast, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TheSkullsporeNexusReductionEffect()));

        // Whenever one or more nontoken creatures you control die, create a green Fungus Dinosaur creature token with base power and toughness each equal to the total power of those creatures.
        this.addAbility(new TheSkullsporeNexusTrigger());

        // {2}, {T}: Double target creature's power until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new TheSkullsporeNexusDoubleEffect(),
                new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TheSkullsporeNexus(final TheSkullsporeNexus card) {
        super(card);
    }

    @Override
    public TheSkullsporeNexus copy() {
        return new TheSkullsporeNexus(this);
    }
}

class TheSkullsporeNexusReductionEffect extends CostModificationEffectImpl {

    TheSkullsporeNexusReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is the greatest power among creatures you control";
    }

    private TheSkullsporeNexusReductionEffect(final TheSkullsporeNexusReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield()
                .getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, abilityToModify.getControllerId(), game
                ).stream()
                .map(Permanent::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        CardUtil.reduceCost(abilityToModify, Math.max(0, reductionAmount));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public TheSkullsporeNexusReductionEffect copy() {
        return new TheSkullsporeNexusReductionEffect(this);
    }
}

class TheSkullsporeNexusTrigger extends TriggeredAbilityImpl {

    TheSkullsporeNexusTrigger() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private TheSkullsporeNexusTrigger(final TheSkullsporeNexusTrigger ability) {
        super(ability);
    }

    @Override
    public TheSkullsporeNexusTrigger copy() {
        return new TheSkullsporeNexusTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }

        List<Permanent> permanents = zEvent.getCards().stream()
                .map(MageItem::getId)
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .filter(p -> p.isCreature(game) && p.isControlledBy(getControllerId()))
                .collect(Collectors.toList());

        if (permanents.isEmpty()) {
            return false;
        }

        int amount = permanents.stream().mapToInt(p -> p.getPower().getValue()).sum();

        this.getEffects().clear();
        Effect effect = new CreateTokenEffect(new FungusDinosaurToken(amount));
        effect.setValue(infoKey, amount);
        this.getEffects().add(effect);
        return true;
    }

    private static final String infoKey = "totalpower";

    @Override
    public String getRule() {
        // this trigger might want to expose extra info on the stack
        String triggeredInfo = "";
        if (!this.getEffects().isEmpty()) {
            triggeredInfo += "<br><br><i>Total power: " + this.getEffects().get(0).getValue(infoKey) + "</i>";
        }
        return "Whenever one or more nontoken creatures you control die, "
                + "create a green Fungus Dinosaur creature token with base power and toughness "
                + "each equal to the total power of those creatures." + triggeredInfo;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }
}

class TheSkullsporeNexusDoubleEffect extends OneShotEffect {

    TheSkullsporeNexusDoubleEffect() {
        super(Outcome.Benefit);
        staticText = "double target creature's power until end of turn";
    }

    private TheSkullsporeNexusDoubleEffect(final TheSkullsporeNexusDoubleEffect effect) {
        super(effect);
    }

    @Override
    public TheSkullsporeNexusDoubleEffect copy() {
        return new TheSkullsporeNexusDoubleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        ContinuousEffect boost = new BoostTargetEffect(permanent.getPower().getValue(), 0, Duration.EndOfTurn)
                .setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(boost, source);
        return true;
    }
}
