package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterArtifactSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinArtisans extends CardImpl {

    public GoblinArtisans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Flip a coin. If you win the flip, draw a card. If you lose the flip, counter target artifact spell you control that isn't the target of an ability from another creature named Goblin Artisans.
        Ability ability = new SimpleActivatedAbility(new FlipCoinEffect(
                new DrawCardSourceControllerEffect(1), new CounterTargetEffect()
        ), new TapSourceCost());
        ability.addTarget(new GoblinArtisansTarget());
        this.addAbility(ability);
    }

    private GoblinArtisans(final GoblinArtisans card) {
        super(card);
    }

    @Override
    public GoblinArtisans copy() {
        return new GoblinArtisans(this);
    }
}

class GoblinArtisansTarget extends TargetSpell {

    private static final FilterSpell filter = new FilterArtifactSpell(
            "target artifact spell you control that isn't the target " +
                    "of an ability from another creature named Goblin Artisans"
    );

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    GoblinArtisansTarget() {
        super(filter);
    }

    private GoblinArtisansTarget(final GoblinArtisansTarget target) {
        super(target);
    }

    @Override
    public GoblinArtisansTarget copy() {
        return new GoblinArtisansTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        MageObjectReference sourceRef = new MageObjectReference(source.getSourceObject(game), game);
        Spell spell = game.getSpell(id);
        if (spell == null) {
            return false;
        }
        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof StackAbility)) {
                continue;
            }
            Permanent permanent = ((StackAbility) stackObject).getSourcePermanentOrLKI(game);
            if (permanent != null
                    && !sourceRef.refersTo(permanent, game)
                    && permanent.isCreature(game)
                    && "Goblin Artisans".equals(permanent.getName())
                    && stackObject
                    .getStackAbility()
                    .getTargets()
                    .stream()
                    .map(Target::getTargets)
                    .flatMap(Collection::stream)
                    .anyMatch(id::equals)) {
                return false;
            }
        }
        return true;
    }
}
