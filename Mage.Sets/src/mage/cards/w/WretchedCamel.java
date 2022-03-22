package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.DesertControlledOrGraveyardCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class WretchedCamel extends CardImpl {

    public WretchedCamel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Wretched Camel dies, if you control a Desert or there is a Desert card in your graveyard, target player discards a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new DiscardTargetEffect(1)),
                DesertControlledOrGraveyardCondition.instance, "When {this} dies, " +
                "if you control a Desert or there is a Desert card in your graveyard, target player discards a card."
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.addHint(DesertControlledOrGraveyardCondition.getHint()));
    }

    private WretchedCamel(final WretchedCamel card) {
        super(card);
    }

    @Override
    public WretchedCamel copy() {
        return new WretchedCamel(this);
    }
}
