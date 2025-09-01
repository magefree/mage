package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerfolkWindrobber extends CardImpl {

    public MerfolkWindrobber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Merfolk Windrobber deals combat damage to a player, that player mills a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new MillCardsTargetEffect(1), false, true
        ));

        // Sacrifice Merfolk Windrobber: Draw a card. Activate this ability only if an opponent has eight or more cards in their graveyard.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new SacrificeSourceCost(), CardsInOpponentGraveyardCondition.EIGHT
        ).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));
    }

    private MerfolkWindrobber(final MerfolkWindrobber card) {
        super(card);
    }

    @Override
    public MerfolkWindrobber copy() {
        return new MerfolkWindrobber(this);
    }
}
