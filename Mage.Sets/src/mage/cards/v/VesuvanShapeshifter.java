package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class VesuvanShapeshifter extends CardImpl {

    public VesuvanShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Vesuvan Shapeshifter turned face up, may choose another creature. If you do, until Vesuvan Shapeshifter is turned face down, it becomes a copy of that creature
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AsTurnedFaceUpEffect(new VesuvanShapeshifterEffect(), false));
        ability.setWorksFaceDown(true);
        this.addAbility(ability);

        // As Vesuvan Shapeshifter etbs, you may choose another creature. If you do, until Vesuvan Shapeshifter is turned face down, it becomes a copy of that creature
        Effect effect = new CopyPermanentEffect(StaticFilters.FILTER_PERMANENT_CREATURE, new VesuvanShapeShifterFaceUpApplier());
        effect.setText("as a copy of any creature on the battlefield until {this} is turned faced down");
        ability = new EntersBattlefieldAbility(effect, true);
        ability.setWorksFaceDown(false);
        this.addAbility(ability);

        // and has "At the beginning of your upkeep, you may turn this creature face down".
        effect = new VesuvanShapeshifterFaceDownEffect();
        ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, true);
        this.addAbility(ability);

        // Morph {1}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{1}{U}")));
    }

    public VesuvanShapeshifter(final VesuvanShapeshifter card) {
        super(card);
    }

    @Override
    public VesuvanShapeshifter copy() {
        return new VesuvanShapeshifter(this);
    }
}

class VesuvanShapeShifterFaceUpApplier extends ApplyToPermanent {

    @Override
    public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
        Effect effect = new VesuvanShapeshifterFaceDownEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        permanent.getAbilities().add(ability);
        return true;
    }

    @Override
    public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
        Effect effect = new VesuvanShapeshifterFaceDownEffect();
        Ability ability = new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, true);
        mageObject.getAbilities().add(ability);
        return true;
    }
}

class VesuvanShapeshifterEffect extends OneShotEffect {

    public VesuvanShapeshifterEffect() {
        super(Outcome.Copy);
        staticText = "have {this} become a copy of a creature, except it has this ability";
    }

    public VesuvanShapeshifterEffect(final VesuvanShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanShapeshifterEffect copy() {
        return new VesuvanShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        Permanent copyToCreature = game.getPermanent(source.getSourceId());
        if (copyToCreature != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
            filter.add(AnotherPredicate.instance);

            TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1, filter, true);

            if (controller != null && controller.chooseTarget(Outcome.BecomeCreature, target, source, game) && !target.getTargets().isEmpty()) {
                Permanent copyFromCreature = game.getPermanentOrLKIBattlefield(target.getFirstTarget());
                if (copyFromCreature != null) {
                    game.copyPermanent(Duration.Custom, copyFromCreature, copyToCreature.getId(), source, new VesuvanShapeShifterFaceUpApplier());
                    source.getTargets().clear();
                    game.getState().processAction(game); // needed to get effects ready if copy happens in replacment and the copied abilities react of the same event (e.g. turn face up)
                    return true;
                }
            }
        }
        return false;
    }
}

class VesuvanShapeshifterFaceDownEffect extends OneShotEffect {

    public VesuvanShapeshifterFaceDownEffect() {
        super(Outcome.Copy);
        staticText = "have {this} become a morphed, faced down creature";
    }

    public VesuvanShapeshifterFaceDownEffect(final VesuvanShapeshifterFaceDownEffect effect) {
        super(effect);
    }

    @Override
    public VesuvanShapeshifterFaceDownEffect copy() {
        return new VesuvanShapeshifterFaceDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            permanent.removeAllAbilities(source.getSourceId(), game);

            // Set any previous copy effects to 'discarded'
            for (Effect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof CopyEffect) {
                    CopyEffect copyEffect = (CopyEffect) effect;
                    if (copyEffect.getSourceId().equals(permanent.getId())) {
                        copyEffect.discard();
                    }
                }
            }

            permanent.turnFaceDown(game, source.getControllerId());
            permanent.setManifested(false);
            permanent.setMorphed(true);
            return permanent.isFaceDown(game);
        }

        return false;
    }
}
