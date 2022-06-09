
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class PaleWayfarer extends CardImpl {

    public PaleWayfarer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{W}{W}, {untap}: Target creature gains protection from the color of its controller's choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PaleWayfarerEffect(), new ManaCostsImpl<>("{2}{W}{W}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private PaleWayfarer(final PaleWayfarer card) {
        super(card);
    }

    @Override
    public PaleWayfarer copy() {
        return new PaleWayfarer(this);
    }
}

class PaleWayfarerEffect extends OneShotEffect {

    public PaleWayfarerEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature gains protection from the color of its controller's choice until end of turn";
    }

    public PaleWayfarerEffect(final PaleWayfarerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            if (player != null) {
                ChoiceColor colorChoice = new ChoiceColor();
                if (player.choose(Outcome.Neutral, colorChoice, game)) {
                    game.informPlayers(targetCreature.getName() + ": " + player.getLogName() + " has chosen " + colorChoice.getChoice());
                    game.getState().setValue(targetCreature.getId() + "_color", colorChoice.getColor());

                    ObjectColor protectColor = (ObjectColor) game.getState().getValue(targetCreature.getId() + "_color");
                    if (protectColor != null) {
                        ContinuousEffect effect = new ProtectionChosenColorTargetEffect();
                        effect.setTargetPointer(new FixedTarget(targetCreature, game));
                        game.addEffect(effect, source);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PaleWayfarerEffect copy() {
        return new PaleWayfarerEffect(this);
    }
}

class ProtectionChosenColorTargetEffect extends ContinuousEffectImpl {

    protected ObjectColor chosenColor;
    protected ProtectionAbility protectionAbility;

    public ProtectionChosenColorTargetEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    public ProtectionChosenColorTargetEffect(final ProtectionChosenColorTargetEffect effect) {
        super(effect);
        if (effect.chosenColor != null) {
            this.chosenColor = effect.chosenColor.copy();
        }
        if (effect.protectionAbility != null) {
            this.protectionAbility = effect.protectionAbility.copy();
        }
    }

    @Override
    public ProtectionChosenColorTargetEffect copy() {
        return new ProtectionChosenColorTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (color != null && (protectionAbility == null || !color.equals(chosenColor))) {
                chosenColor = color;
                FilterObject protectionFilter = new FilterObject(chosenColor.getDescription());
                protectionFilter.add(new ColorPredicate(chosenColor));
                protectionAbility = new ProtectionAbility(protectionFilter);
            }
            if (protectionAbility != null) {
                permanent.addAbility(protectionAbility, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }
}
