package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.MaxManaValueControlledCreatureOrPlaneswalkerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FlareOfMalice extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken black creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private static final FilterPermanent filterCreatureOrPlaneswalker = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker with the greatest mana value " +
                    "among creatures and planeswalkers they control"
    );

    static {
        filterCreatureOrPlaneswalker.add(MaxManaValueControlledCreatureOrPlaneswalkerPredicate.instance);
    }

    public FlareOfMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // You may sacrifice a nontoken black creature rather than pay this spell's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(filter)).setRuleAtTheTop(true));

        // Each opponent sacrifices a creature or planeswalker with the greatest converted mana value among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private FlareOfMalice(final FlareOfMalice card) {
        super(card);
    }

    @Override
    public FlareOfMalice copy() {
        return new FlareOfMalice(this);
    }
}
