package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author AhmadYProjects
 */
public final class AgainstAllOdds extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact or creature you control");
    private static final FilterCard filter2 = new FilterCard("artifact or creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public AgainstAllOdds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Exile target artifact or creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter));
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false));

        // * Return target artifact or creature card with mana value 3 or less from your graveyard to the battlefield.
        Mode mode2 = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode2.addTarget(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode2);
    }

    private AgainstAllOdds(final AgainstAllOdds card) {
        super(card);
    }

    @Override
    public AgainstAllOdds copy() {
        return new AgainstAllOdds(this);
    }
}
