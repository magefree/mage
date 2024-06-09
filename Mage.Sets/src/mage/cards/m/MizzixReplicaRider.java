package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MizzixReplicaRider extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public MizzixReplicaRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell from anywhere other than your hand, you may pay {1}{U/R}. If you do, copy that spell and you may choose new targets for the copy. If the copy is a permanent spell, it gains haste and "At the beginning of your end step, sacrifice this permanent."
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new MizzixReplicaRiderEffect(), new ManaCostsImpl<>("{1}{U/R}")
        ), filter, false));
    }

    private MizzixReplicaRider(final MizzixReplicaRider card) {
        super(card);
    }

    @Override
    public MizzixReplicaRider copy() {
        return new MizzixReplicaRider(this);
    }
}

class MizzixReplicaRiderEffect extends OneShotEffect {

    MizzixReplicaRiderEffect() {
        super(Outcome.Benefit);
        staticText = "copy that spell and you may choose new targets for the copy. " +
                "If the copy is a permanent spell, it gains haste and " +
                "\"At the beginning of your end step, sacrifice this permanent.\"";
    }

    private MizzixReplicaRiderEffect(final MizzixReplicaRiderEffect effect) {
        super(effect);
    }

    @Override
    public MizzixReplicaRiderEffect copy() {
        return new MizzixReplicaRiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), true,
                1, MizzixReplicaRiderApplier.instance
        );
        return true;
    }
}

enum MizzixReplicaRiderApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        if (!stackObject.isPermanent(game)) {
            return;
        }
        Spell spell = (Spell) stackObject;
        spell.addAbilityForCopy(HasteAbility.getInstance());
        spell.addAbilityForCopy(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceEffect().setText("sacrifice this permanent"), TargetController.YOU, false
        ));
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}