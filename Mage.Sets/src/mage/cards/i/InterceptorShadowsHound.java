package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InterceptorShadowsHound extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ASSASSIN, "Assassins");

    public InterceptorShadowsHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Assassins you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter
        )));

        // Whenever you attack with one or more legendary creatures, you may pay {2}{B}. If you do, return this card from your graveyard to the battlefield tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToBattlefieldEffect(
                                true, false, false, true
                        ), new ManaCostsImpl<>("{2}{B}")
                ), 1, StaticFilters.FILTER_CREATURES_LEGENDARY
        ));
    }

    private InterceptorShadowsHound(final InterceptorShadowsHound card) {
        super(card);
    }

    @Override
    public InterceptorShadowsHound copy() {
        return new InterceptorShadowsHound(this);
    }
}
