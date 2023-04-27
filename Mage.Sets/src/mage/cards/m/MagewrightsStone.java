
package mage.cards.m;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class MagewrightsStone extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that has an activated ability with {T} in its cost");

    static {
        filter.add(new HasAbilityWithTapSymbolPredicate());
    }

    public MagewrightsStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}: Untap target creature that has an activated ability with {T} in its cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private MagewrightsStone(final MagewrightsStone card) {
        super(card);
    }

    @Override
    public MagewrightsStone copy() {
        return new MagewrightsStone(this);
    }
}

class HasAbilityWithTapSymbolPredicate implements Predicate<MageObject> {

    @Override
    public boolean apply(MageObject input, Game game) {
        Abilities<Ability> abilities;
        if (input instanceof Card) {
            abilities = ((Card) input).getAbilities(game);
        } else {
            abilities = input.getAbilities();
        }

        for (Ability ability : abilities) {
            if ((ability.getAbilityType() == AbilityType.ACTIVATED || ability.getAbilityType() == AbilityType.MANA) && !ability.getCosts().isEmpty()) {
                if (ability.hasTapCost()) {
                        return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "activated ability with {T} in its cost";
    }
}
