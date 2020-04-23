package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurvivorsBond extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("Human creature card from your graveyard");
    private static final FilterCard filter2
            = new FilterCreatureCard("non-Human creature card from your graveyard");

    static {
        filter.add(SubType.HUMAN.getPredicate());
        filter2.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public SurvivorsBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Return target Human creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // • Return target non-Human creature card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private SurvivorsBond(final SurvivorsBond card) {
        super(card);
    }

    @Override
    public SurvivorsBond copy() {
        return new SurvivorsBond(this);
    }
}
