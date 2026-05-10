package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitherbloomCommand extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("noncreature, nonland permanent with mana value 2 or less");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public WitherbloomCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Target player mills three cards, then you return a land card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(3));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(false,
                StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD, PutCards.HAND).concatBy(", then you"));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // • Destroy target noncreature, nonland permanent with mana value 2 or less.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addMode(mode);

        // • Target creature gets -3/-1 until end of turn.
        mode = new Mode(new BoostTargetEffect(-3, -1));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // • Target opponent loses 2 life and you gain 2 life.
        mode = new Mode(new LoseLifeTargetEffect(2));
        mode.addEffect(new GainLifeEffect(2).concatBy("and"));
        mode.addTarget(new TargetOpponent());
        this.getSpellAbility().addMode(mode);
    }

    private WitherbloomCommand(final WitherbloomCommand card) {
        super(card);
    }

    @Override
    public WitherbloomCommand copy() {
        return new WitherbloomCommand(this);
    }
}
