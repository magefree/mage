package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class VividRevival extends CardImpl {

    private static final FilterCard filter = new FilterCard("multicolored cards from your graveyard");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public VividRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Return up to three target multicolor cards from your graveyard to your hand. Exile Vivid Revival.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, filter));
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private VividRevival(final VividRevival card) {
        super(card);
    }

    @Override
    public VividRevival copy() {
        return new VividRevival(this);
    }
}
