package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.TatsumaDragonToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

import java.util.HashSet;
import java.util.Set;
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
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(5, 5)));

        // {6}, Exile Tatsumasa, the Dragon's Fang: Create a 5/5 blue Dragon Spirit creature token with flying. Return Tatsumasa to the battlefield under its owner's control when that token dies.
        Ability ability = new SimpleActivatedAbility(new TatsumaTheDragonsFangEffect(), new GenericManaCost(6));
        ability.addCost(new ExileSourceCost());
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
        this.staticText = "Create a 5/5 blue Dragon Spirit creature token with flying. " +
                "Return {this} to the battlefield under its owner's control when that token dies";
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
        Token token = new TatsumaDragonToken();
        token.putOntoBattlefield(1, game, source);
        game.addDelayedTriggeredAbility(new TatsumaTheDragonsFangTriggeredAbility(token, source, game), source);
        return true;
    }
}

class TatsumaTheDragonsFangTriggeredAbility extends DelayedTriggeredAbility {

    private final Set<UUID> tokens = new HashSet<>();

    public TatsumaTheDragonsFangTriggeredAbility(Token token, Ability source, Game game) {
        super(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false).setTargetPointer(new FixedTarget(new MageObjectReference(source, 1))), Duration.Custom, false, false);
        tokens.addAll(token.getLastAddedTokenIds());
    }

    public TatsumaTheDragonsFangTriggeredAbility(final TatsumaTheDragonsFangTriggeredAbility ability) {
        super(ability);
        tokens.addAll(ability.tokens);
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
        return ((ZoneChangeEvent) event).isDiesEvent() && tokens.contains(event.getTargetId());
    }

    @Override
    public String getRule() {
        return "Return {this} to the battlefield under its owner's control when that token dies.";
    }
}
