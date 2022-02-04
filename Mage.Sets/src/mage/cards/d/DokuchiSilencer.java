package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DokuchiSilencer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DokuchiSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Ninjutsu {1}{B}
        this.addAbility(new NinjutsuAbility("{1}{B}"));

        // Whenever Dokuchi Silencer deals combat damage to a player, you may discard a creature card. When you do, destroy target creature or planeswalker an opponent controls.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DoWhenCostPaid(
                        ability, new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE)),
                        "Discard a creature card?"
                ), false
        ));
    }

    private DokuchiSilencer(final DokuchiSilencer card) {
        super(card);
    }

    @Override
    public DokuchiSilencer copy() {
        return new DokuchiSilencer(this);
    }
}
