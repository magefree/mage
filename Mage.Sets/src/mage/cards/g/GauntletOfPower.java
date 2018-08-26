package mage.cards.g;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class GauntletOfPower extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("a basic land");

    static {
        filter.add(new SupertypePredicate(SuperType.BASIC));
    }

    public GauntletOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As Gauntlet of Power enters the battlefield, choose a color.
        this.addAbility(new EntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));
        // Creatures of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GauntletOfPowerEffect1()));

        // Whenever a basic land is tapped for mana of the chosen color, its controller adds one mana of that color.
        this.addAbility(new TapForManaAllTriggeredAbility(new GauntletOfPowerEffectEffect2(), filter, SetTargetPointer.PERMANENT));
    }

    public GauntletOfPower(final GauntletOfPower card) {
        super(card);
    }

    @Override
    public GauntletOfPower copy() {
        return new GauntletOfPower(this);
    }
}

class GauntletOfPowerEffect1 extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public GauntletOfPowerEffect1() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures of the chosen color get +1/+1";
    }

    public GauntletOfPowerEffect1(final GauntletOfPowerEffect1 effect) {
        super(effect);
    }

    @Override
    public GauntletOfPowerEffect1 copy() {
        return new GauntletOfPowerEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (perm.getColor(game).contains(color)) {
                    perm.addPower(1);
                    perm.addToughness(1);
                }
            }
        }
        return true;
    }

}

class TapForManaAllTriggeredAbility extends TriggeredManaAbility {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public TapForManaAllTriggeredAbility(ManaEffect effect, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TapForManaAllTriggeredAbility(TapForManaAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            ManaEvent mEvent = (ManaEvent) event;
            ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
            if (color != null) {
                Mana mana = mEvent.getMana();
                boolean colorFits = false;
                if (color.isBlack() && mana.getBlack() > 0) {
                    colorFits = true;
                } else if (color.isBlue() && mana.getBlue() > 0) {
                    colorFits = true;
                } else if (color.isGreen() && mana.getGreen() > 0) {
                    colorFits = true;
                } else if (color.isWhite() && mana.getWhite() > 0) {
                    colorFits = true;
                } else if (color.isRed() && mana.getRed() > 0) {
                    colorFits = true;
                }
                if (colorFits) {

                    for (Effect effect : getEffects()) {
                        effect.setValue("mana", mEvent.getMana());
                    }
                    switch (setTargetPointer) {
                        case PERMANENT:
                            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                            break;
                        case PLAYER:
                            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TapForManaAllTriggeredAbility copy() {
        return new TapForManaAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a basic land is tapped for mana of the chosen color, "
                + "its controller adds an additional one mana of that color.";
    }
}

class GauntletOfPowerEffectEffect2 extends ManaEffect {

    public GauntletOfPowerEffectEffect2() {
        super();
        staticText = "its controller adds one additional mana of that color";
    }

    public GauntletOfPowerEffectEffect2(final GauntletOfPowerEffectEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (land != null) {
            Player player = game.getPlayer(land.getControllerId());
            if (player != null) {
                player.getManaPool().addMana(getMana(game, source), game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Permanent land = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (land != null) {
            Mana mana = (Mana) getValue("mana");
            if (mana != null) {
                return mana.copy();
            }
        }
        return null;
    }

    @Override
    public GauntletOfPowerEffectEffect2 copy() {
        return new GauntletOfPowerEffectEffect2(this);
    }
}
