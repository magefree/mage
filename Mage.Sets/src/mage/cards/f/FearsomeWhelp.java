
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;

import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CardsInYourHandPerpetuallyGainEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterBySubtypeCard;

/**
 *
 * @author karapuzz14
 */
public final class FearsomeWhelp extends CardImpl {

    public FearsomeWhelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, each Dragon card in your hand perpetually gains “This spell costs {1} less to cast.”
        Ability reduceCostAbility = new SimpleStaticAbility(new SpellCostReductionSourceEffect(1));
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(
                new CardsInYourHandPerpetuallyGainEffect(reduceCostAbility, new FilterBySubtypeCard(SubType.DRAGON))
                        .setText(" each Dragon card in your hand perpetually gains \"This spell costs {1} less to cast.\""),
                false));
    }

    private FearsomeWhelp(final FearsomeWhelp card) {
        super(card);
    }

    @Override
    public FearsomeWhelp copy() {
        return new FearsomeWhelp(this);
    }
}
