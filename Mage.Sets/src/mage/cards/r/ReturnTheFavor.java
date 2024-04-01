package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.abilities.effects.common.CopyTargetStackAbilityEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnTheFavor extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("instant spell, sorcery spell, activated ability, or triggered ability");
    private static final FilterStackObject filter2 = new FilterStackObject("spell or ability with a single target");

    static {
        filter.add(ReturnTheFavorPredicate.instance);
        filter2.add(new NumberOfTargetsPredicate(1));
    }

    public ReturnTheFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Copy target instant spell, sorcery spell, activated ability, or triggered ability. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetStackAbilityEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {1} -- Change the target of target spell or ability with a single target.
        this.getSpellAbility().addMode(new Mode(new ChooseNewTargetsTargetEffect(true, true))
                .addTarget(new TargetStackObject(filter2))
                .withCost(new GenericManaCost(1)));
    }

    private ReturnTheFavor(final ReturnTheFavor card) {
        super(card);
    }

    @Override
    public ReturnTheFavor copy() {
        return new ReturnTheFavor(this);
    }
}

enum ReturnTheFavorPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        return !(input instanceof Spell) || input.isInstantOrSorcery(game);
    }
}
