package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TatsumaDragonToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TatsumasaTheDragonsFang extends CardImpl {

    public TatsumasaTheDragonsFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +5/+5.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(5, 5)));

        // {6}, Exile Tatsumasa, the Dragon's Fang: Create a 5/5 blue Dragon Spirit creature token with flying. Return Tatsumasa to the battlefield under its owner's control when that token dies.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TatsumaTheDragonsFangEffect(), new GenericManaCost(6));
        ability.addCost(new ExileSourceCost(true));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3), false));
    }

    private TatsumasaTheDragonsFang(final TatsumasaTheDragonsFang card) {
        super(card);
    }

    @Override
    public TatsumasaTheDragonsFang copy() {
        return new TatsumasaTheDragonsFang(this);
    }
}

class TatsumaTheDragonsFangEffect extends OneShotEffect {

    public TatsumaTheDragonsFangEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 5/5 blue Dragon Spirit creature token with flying. Return Tatsumasa to the battlefield under its owner's control when that token dies";
    }

    public TatsumaTheDragonsFangEffect(final TatsumaTheDragonsFangEffect effect) {
        super(effect);
    }

    @Override
    public TatsumaTheDragonsFangEffect copy() {
        return new TatsumaTheDragonsFangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new TatsumaDragonToken());
        effect.apply(game, source);
        for (UUID tokenId : effect.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                FixedTarget fixedTarget = new FixedTarget(tokenPermanent, game);
                Effect returnEffect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                returnEffect.setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())));
                DelayedTriggeredAbility delayedAbility = new TatsumaTheDragonsFangTriggeredAbility(fixedTarget, returnEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
        }

        return true;
    }
}

class TatsumaTheDragonsFangTriggeredAbility extends DelayedTriggeredAbility {

    protected FixedTarget fixedTarget;

    public TatsumaTheDragonsFangTriggeredAbility(FixedTarget fixedTarget, Effect effect) {
        super(effect, Duration.OneUse);
        this.fixedTarget = fixedTarget;
    }

    public TatsumaTheDragonsFangTriggeredAbility(final TatsumaTheDragonsFangTriggeredAbility ability) {
        super(ability);
        this.fixedTarget = ability.fixedTarget;
    }

    @Override
    public TatsumaTheDragonsFangTriggeredAbility copy() {
        return new TatsumaTheDragonsFangTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            if (fixedTarget.getFirst(game, this).equals(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Return {this} to the battlefield under its owner's control when that token dies.";
    }
}
