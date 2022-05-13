package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OtawaraSoaringCity extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("artifact, creature, enchantment, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public OtawaraSoaringCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // Channel — {3}{U}, Discard Otawara, Soaring City: Return target artifact, creature, enchantment, or planeswalker to its owner's hand. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{3}{U}", new ReturnToHandTargetEffect());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each legendary creature you control"));
        ability.addTarget(new TargetPermanent(filter));
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability.addHint(LegendaryCreatureCostAdjuster.getHint()));
    }

    private OtawaraSoaringCity(final OtawaraSoaringCity card) {
        super(card);
    }

    @Override
    public OtawaraSoaringCity copy() {
        return new OtawaraSoaringCity(this);
    }
}
