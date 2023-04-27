
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
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
import mage.constants.TargetController;
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
public final class Wishmonger extends CardImpl {

    public Wishmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.UNICORN);
        this.subtype.add(SubType.MONGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target creature gains protection from the color of its controller's choice until end of turn. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WishmongerEffect(), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private Wishmonger(final Wishmonger card) {
        super(card);
    }

    @Override
    public Wishmonger copy() {
        return new Wishmonger(this);
    }
}

class WishmongerEffect extends OneShotEffect {

    public WishmongerEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target creature gains protection from the color of its controller's choice until end of turn";
    }

    public WishmongerEffect(final WishmongerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            ChoiceColor colorChoice = new ChoiceColor();
            if (player != null && player.choose(Outcome.Neutral, colorChoice, game)) {
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

        return false;
    }

    @Override
    public WishmongerEffect copy() {
        return new WishmongerEffect(this);

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
