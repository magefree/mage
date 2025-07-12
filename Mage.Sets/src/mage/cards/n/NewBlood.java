package mage.cards.n;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NewBlood extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.VAMPIRE, "an untapped Vampire you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public NewBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // As an additional cost to cast New Blood, tap an untapped Vampire you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(filter)));

        // Gain control of target creature. Change the text of that creature by replacing all instances of one creature type with Vampire.
        getSpellAbility().addEffect(new NewBloodEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private NewBlood(final NewBlood card) {
        super(card);
    }

    @Override
    public NewBlood copy() {
        return new NewBlood(this);
    }
}

class NewBloodEffect extends OneShotEffect {

    NewBloodEffect() {
        super(Outcome.Benefit);
        this.staticText = "Gain control of target creature. Change the text of that creature by replacing all instances of one creature type with Vampire";
    }

    private NewBloodEffect(final NewBloodEffect effect) {
        super(effect);
    }

    @Override
    public NewBloodEffect copy() {
        return new NewBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true);
            effect.setTargetPointer(new FixedTarget(targetPermanent, game));
            game.addEffect(effect, source);
            effect = new ChangeCreatureTypeTargetEffect(null, SubType.VAMPIRE, Duration.Custom);
            effect.setTargetPointer(new FixedTarget(targetPermanent, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}

class ChangeCreatureTypeTargetEffect extends ContinuousEffectImpl {

    private SubType fromSubType;
    private final SubType toSubType;

    public ChangeCreatureTypeTargetEffect(SubType fromSubType, SubType toSubType, Duration duration) {
        super(duration, Layer.TextChangingEffects_3, SubLayer.NA, Outcome.Benefit);
        this.fromSubType = fromSubType;
        this.toSubType = toSubType;
        this.staticText = "Change the text of that creature by replacing all instances of one creature type with Vampire";
    }

    private ChangeCreatureTypeTargetEffect(final ChangeCreatureTypeTargetEffect effect) {
        super(effect);
        this.fromSubType = effect.fromSubType;
        this.toSubType = effect.toSubType;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }
        if (fromSubType == null) {
            Choice typeChoice = new ChoiceCreatureType(game, source);
            typeChoice.setMessage("Choose creature type to change to Vampire");
            if (!controller.choose(outcome, typeChoice, game)) {
                discard();
                return;
            }
            fromSubType = SubType.byDescription(typeChoice.getChoiceKey());
            if (!game.isSimulation()) {
                game.informPlayers(controller.getLogName() + " has chosen the creature type: " + fromSubType.toString());
            }
        }
    }
    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            MageObject mageObject = (MageObject) object;
            mageObject.removeSubType(game, fromSubType);
            mageObject.addSubType(game, toSubType);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            if (duration == Duration.Custom) {
                discard();
            }
            return false;
        }
        if (fromSubType == null) {
            throw new UnsupportedOperationException("No subtype to change set");
        }
        boolean objectFound = false;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            MageObject targetObject = game.getObject(targetId);
            if (targetObject != null) {
                objectFound = true;
                if (targetObject.hasSubtype(fromSubType, game)) {
                    affectedObjects.add(targetObject);
                }
            }
        }
        if (!objectFound && this.getDuration() == Duration.Custom) {
            this.discard();
        }
        return true;
    }

    @Override
    public ChangeCreatureTypeTargetEffect copy() {
        return new ChangeCreatureTypeTargetEffect(this);
    }
}
