package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.abilities.effects.common.continuous.AddCreatureTypeAdditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class EverAfter extends CardImpl {

    public EverAfter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Return up to two target creature cards from your graveyard to the battlefield. Each of those creatures is a black Zombie in addition
        // to its other colors and types. Put Ever After on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        Effect effect = new AddCreatureTypeAdditionEffect(SubType.ZOMBIE, true);
        effect.setText("Each of those creatures is a black Zombie in addition to its other colors and types");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private EverAfter(final EverAfter card) {
        super(card);
    }

    @Override
    public EverAfter copy() {
        return new EverAfter(this);
    }
}
