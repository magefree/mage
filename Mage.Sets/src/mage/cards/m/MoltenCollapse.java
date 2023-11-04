package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MoltenCollapse extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature, nonland permanent with mana value 1 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 1));
    }

    public MoltenCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // Choose one. If you descended this turn, you may choose both instead.
        this.getSpellAbility().getModes().setMoreCondition(DescendedThisTurnCondition.instance);
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you descended this turn, you may choose both instead."
        );
        this.getSpellAbility().addHint(DescendCondition.getHint());
        this.getSpellAbility().addWatcher(new DescendedWatcher());

        // * Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // * Destroy target noncreature, nonland permanent with mana value 1 or less.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private MoltenCollapse(final MoltenCollapse card) {
        super(card);
    }

    @Override
    public MoltenCollapse copy() {
        return new MoltenCollapse(this);
    }
}
