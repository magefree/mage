package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class DonalHeraldOfWings extends CardImpl {

    public DonalHeraldOfWings(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);

        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a nonlegendary creature spell with flying, you may copy it,
        // except the copy is a 1/1 Spirit in addition to its other types.
        // Do this only once each turn. (The copy becomes a token.)
        // TODO: This still triggers and asks if you wanna use it, even if you've used it once this turn.
        this.addAbility(new DonalHeraldOfWingsTriggeredAbility());
    }

    private DonalHeraldOfWings(final DonalHeraldOfWings card) { super(card); }

    @Override
    public DonalHeraldOfWings copy() { return new DonalHeraldOfWings(this); }
}

class DonalHeraldOfWingsTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterSpell filterSpell = new FilterSpell("a nonlegendary creature spell with flying");
    static {
        filterSpell.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filterSpell.add(CardType.CREATURE.getPredicate());
    }

    DonalHeraldOfWingsTriggeredAbility() {
        super(new DonalHeraldOfWingsEffect(), filterSpell, true, true);
    }

    private DonalHeraldOfWingsTriggeredAbility(final DonalHeraldOfWingsTriggeredAbility ability) { super(ability); }

    @Override
    public DonalHeraldOfWingsTriggeredAbility copy() {
        return new DonalHeraldOfWingsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return abilityAvailableThisTurn(game) && super.checkTrigger(event, game);
    }

    @Override
    public boolean resolve(Game game) {
        if (!(abilityAvailableThisTurn(game) && super.resolve(game))) { return false; }
        game.getState().setValue(
                CardUtil.getCardZoneString("lastTurnResolved" + originalId, sourceId, game),
                game.getTurnNum()
        );

        return true;
    }

    private boolean abilityAvailableThisTurn(Game game) {
        Integer lastTurnResolved = (Integer) game.getState().getValue(
                CardUtil.getCardZoneString("lastTurnResolved" + originalId, sourceId, game)
        );
        // A null result is assumed to mean the this ability has not been used yet.
        return lastTurnResolved == null || lastTurnResolved != game.getTurnNum();
    }
}

class DonalHeraldOfWingsEffect extends OneShotEffect {

    DonalHeraldOfWingsEffect() {
        super(Outcome.Copy);
        staticText = "you may copy it, except the copy is a 1/1 Spirit in addition to its other types. " +
                "Do this only once each turn. <i>(The copy becomes a token.)</i>";
    }

    private DonalHeraldOfWingsEffect(final DonalHeraldOfWingsEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        // Get the card that was cast
        if (this.getTargetPointer() == null) { return false; }
        Spell originalSpell = game.getStack().getSpell(((FixedTarget) this.getTargetPointer()).getTarget());

        // Create a token copy
        originalSpell.createCopyOnStack(game, source, controller.getId(), false, 1, DonalHeraldOfWingsApplier.instance);

        return true;
    }

    @Override
    public Effect copy() { return new DonalHeraldOfWingsEffect(this); }
}

enum DonalHeraldOfWingsApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        copiedSpell.addSubType(SubType.SPIRIT);
        copiedSpell.getPower().modifyBaseValue(1);
        copiedSpell.getToughness().modifyBaseValue(1);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType(int copyNumber) { return null; }
}
