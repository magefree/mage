package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToskiBearerOfSecrets extends CardImpl {

    public ToskiBearerOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Toski, Bearer of Secrets attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever a creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));
    }

    private ToskiBearerOfSecrets(final ToskiBearerOfSecrets card) {
        super(card);
    }

    @Override
    public ToskiBearerOfSecrets copy() {
        return new ToskiBearerOfSecrets(this);
    }
}
