
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public final class CagedSun extends CardImpl {

    public CagedSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // As Caged Sun enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Creatures you control of the chosen color get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CagedSunEffect2()));

        // Whenever a land's ability adds one or more mana of the chosen color, add one additional mana of that color.
        this.addAbility(new CagedSunTriggeredAbility());
    }

    public CagedSun(final CagedSun card) {
        super(card);
    }

    @Override
    public CagedSun copy() {
        return new CagedSun(this);
    }
}

class CagedSunEffect2 extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public CagedSunEffect2() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Creatures you control of the chosen color get +1/+1";
    }

    public CagedSunEffect2(final CagedSunEffect2 effect) {
        super(effect);
    }

    @Override
    public CagedSunEffect2 copy() {
        return new CagedSunEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
            if (color != null) {
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                    if (perm.getColor(game).contains(color)) {
                        perm.addPower(1);
                        perm.addToughness(1);
                    }
                }
            }
        }
        return true;
    }

}

class CagedSunTriggeredAbility extends TriggeredManaAbility {

    private static final String staticText = "Whenever a land's ability adds one or more mana of the chosen color, add one additional mana of that color.";

    public CagedSunTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CagedSunEffect());
    }

    public CagedSunTriggeredAbility(CagedSunTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.MANA_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && permanent.isLand()) {
                ObjectColor color = (ObjectColor) game.getState().getValue(this.sourceId + "_color");
                if (color != null && event.getData().contains(color.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CagedSunTriggeredAbility copy() {
        return new CagedSunTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return staticText;
    }
}

class CagedSunEffect extends ManaEffect {

    public CagedSunEffect() {
        super();
    }

    public CagedSunEffect(final CagedSunEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().addMana(getMana(game, source), game, source);
        }
        return true;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color != null) {
            return new Mana(ColoredManaSymbol.lookup(color.toString().charAt(0)));
        } else {
            return null;
        }
    }

    @Override
    public CagedSunEffect copy() {
        return new CagedSunEffect(this);
    }

}
