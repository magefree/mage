
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
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
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class Torchling extends CardImpl {

    public Torchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {R}: Untap Torchling.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R)));

        // {R}: Target creature blocks Torchling this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(), new ColoredManaCost(ColoredManaSymbol.R));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {R}: Change the target of target spell that targets only Torchling.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChooseNewTargetsTargetEffect(true, true), new ColoredManaCost(ColoredManaSymbol.R));
        FilterSpell filter = new FilterSpell("spell that targets only " + this.getName());
        filter.add(new TorchlingTargetPredicate(this.getId()));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

        // {1}: Torchling gets +1/-1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, -1, Duration.EndOfTurn), new GenericManaCost(1)));

        // {1}: Torchling gets -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(-1, 1, Duration.EndOfTurn), new GenericManaCost(1)));
    }

    public Torchling(final Torchling card) {
        super(card);
    }

    @Override
    public Torchling copy() {
        return new Torchling(this);
    }
}

class TorchlingTargetPredicate implements Predicate<MageObject> {

    private final UUID sourceId;

    TorchlingTargetPredicate(UUID sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        Spell spell = game.getStack().getSpell(input.getId());
        if (spell != null) {
            int numberOfTargets = 0;
            for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                for (UUID modeId : spellAbility.getModes().getSelectedModes()) {
                    Mode mode = spellAbility.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetId : target.getTargets()) {
                            if (!targetId.equals(sourceId)) {
                                return false;
                            } else {
                                numberOfTargets++;
                            }
                        }
                    }
                }
            }
            return numberOfTargets > 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "target spell that targets only {this}";
    }
}
