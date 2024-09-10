package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author Grath
 */
public final class Nalfeshnee extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
    }

    public Nalfeshnee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell from exile, copy it. You may choose new targets for the copy. If it’s a permanent spell, the copy gains haste and “At the beginning of the end step, sacrifice this permanent.”
        this.addAbility(new SpellCastControllerTriggeredAbility(new NalfeshneeEffect(), filter, false));
    }

    private Nalfeshnee(final Nalfeshnee card) {
        super(card);
    }

    @Override
    public Nalfeshnee copy() {
        return new Nalfeshnee(this);
    }
}

class NalfeshneeEffect extends OneShotEffect {

    NalfeshneeEffect() {
        super(Outcome.Benefit);
        staticText = "copy it. You may choose new targets for the copy. If it's a permanent spell, the copy gains haste and \"At the beginning of the end step, sacrifice this permanent.\"";
    }

    private NalfeshneeEffect(final NalfeshneeEffect effect) {
        super(effect);
    }

    @Override
    public NalfeshneeEffect copy() {
        return new NalfeshneeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        // Create a token copy if it's a permanent, with haste and "must be sacrificed"
        if (spell.isPermanent()) {
            spell.createCopyOnStack(
                    game, source, controller.getId(), true,
                    1, NalfeshneeApplier.instance
            );
        }
        // Non-permanent spells should not gain haste or "must be sacrificed".
        else {
            spell.createCopyOnStack(
                    game, source, controller.getId(), true
            );
        }
        return true;
    }
}

enum NalfeshneeApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        Spell spell = (Spell) copiedSpell;
        spell.addAbilityForCopy(HasteAbility.getInstance());
        spell.addAbilityForCopy(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, false));
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
