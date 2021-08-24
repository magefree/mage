package mage.cards.g;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GauntletOfPower extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures of the chosen color");

    static {
        filter.add(ChosenColorPredicate.TRUE);
    }

    public GauntletOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // As Gauntlet of Power enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral)));

        // Creatures of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // Whenever a basic land is tapped for mana of the chosen color, its controller adds one mana of that color.
        this.addAbility(new GauntletOfPowerTapForManaAllTriggeredAbility());
    }

    private GauntletOfPower(final GauntletOfPower card) {
        super(card);
    }

    @Override
    public GauntletOfPower copy() {
        return new GauntletOfPower(this);
    }
}

class GauntletOfPowerTapForManaAllTriggeredAbility extends TriggeredManaAbility {

    GauntletOfPowerTapForManaAllTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GauntletOfPowerManaEffect2(), false);
    }

    private GauntletOfPowerTapForManaAllTriggeredAbility(GauntletOfPowerTapForManaAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        TappedForManaEvent mEvent = (TappedForManaEvent) event;
        Permanent permanent = mEvent.getPermanent();
        if (permanent == null || !permanent.isLand() || !permanent.isBasic()) {
            return false;
        }
        ObjectColor color = (ObjectColor) game.getState().getValue(getSourceId() + "_color");
        if (color == null) {
            return false;
        }
        Mana mana = mEvent.getMana();
        if ((!color.isBlack() || mana.getBlack() < 1)
                && (!color.isBlue() || mana.getBlue() < 1)
                && (!color.isGreen() || mana.getGreen() < 1)
                && (!color.isWhite() || mana.getWhite() < 1)
                && (!color.isRed() || mana.getRed() < 1)) {
            return false;
        }

        getEffects().setValue("mana", mEvent.getMana());
        getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public GauntletOfPowerTapForManaAllTriggeredAbility copy() {
        return new GauntletOfPowerTapForManaAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a basic land is tapped for mana of the chosen color, "
                + "its controller adds an additional one mana of that color.";
    }
}

class GauntletOfPowerManaEffect2 extends ManaEffect {

    GauntletOfPowerManaEffect2() {
        super();
        staticText = "its controller adds one additional mana of that color";
    }

    private GauntletOfPowerManaEffect2(final GauntletOfPowerManaEffect2 effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (land != null) {
            return game.getPlayer(land.getControllerId());
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Mana mana = (Mana) getValue("mana");
        if (mana == null) {
            return netMana;
        }
        netMana.add(mana.copy());
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Mana mana = (Mana) getValue("mana");
        if (land == null || mana == null) {
            return new Mana();
        }
        return mana.copy();
    }

    @Override
    public GauntletOfPowerManaEffect2 copy() {
        return new GauntletOfPowerManaEffect2(this);
    }
}
