package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DonatelloGadgetMaster extends CardImpl {

    public DonatelloGadgetMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Sneak {1}{U}
        this.addAbility(new SneakAbility(this, "{1}{U}"));

        // Whenever Donatello deals combat damage to a player, create a token that's a copy of target artifact you control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenCopyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);
    }

    private DonatelloGadgetMaster(final DonatelloGadgetMaster card) {
        super(card);
    }

    @Override
    public DonatelloGadgetMaster copy() {
        return new DonatelloGadgetMaster(this);
    }
}
