package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class CallToTheNetherworld extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("black creature card from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public CallToTheNetherworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Return target black creature card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        // Madness {0}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{0}")));
    }

    private CallToTheNetherworld(final CallToTheNetherworld card) {
        super(card);
    }

    @Override
    public CallToTheNetherworld copy() {
        return new CallToTheNetherworld(this);
    }
}
