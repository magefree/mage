package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;
import java.util.UUID;
import mage.abilities.Mode;

/**
 * @author emerald000
 */
public final class Torchling extends CardImpl {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(TorchlingPredicate.instance);
    }

    public Torchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}: Untap Torchling.
        this.addAbility(new SimpleActivatedAbility(new UntapSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R)));

        // {R}: Target creature blocks Torchling this turn if able.
        Ability ability = new SimpleActivatedAbility(
                new MustBeBlockedByTargetSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {R}: Change the target of target spell that targets only Torchling.
        ability = new SimpleActivatedAbility(
                new ChooseNewTargetsTargetEffect(true, true)
                        .setText("change the target of target spell that targets only {this}"),
                new ColoredManaCost(ColoredManaSymbol.R)
        );
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

        // {1}: Torchling gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)
        ));

        // {1}: Torchling gets -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(-1, 1, Duration.EndOfTurn), new GenericManaCost(1)
        ));
    }

    private Torchling(final Torchling card) {
        super(card);
    }

    @Override
    public Torchling copy() {
        return new Torchling(this);
    }
}

enum TorchlingPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        StackObject stackObject = game.getState().getStack().getStackObject(input.getObject().getId());
        if (stackObject != null) {
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    if (target.getTargets().contains(input.getSourceId()) // contains this card
                            && target.getTargets().size() == 1) { // only one target
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
