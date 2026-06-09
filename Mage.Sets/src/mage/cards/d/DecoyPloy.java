package mage.cards.d;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class DecoyPloy extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.VILLAIN);
    private static final FilterCard filter2 = new FilterCard(SubType.HERO);

    public DecoyPloy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Return target Villain card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        // * Return target Hero card from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private DecoyPloy(final DecoyPloy card) {
        super(card);
    }

    @Override
    public DecoyPloy copy() {
        return new DecoyPloy(this);
    }
}
