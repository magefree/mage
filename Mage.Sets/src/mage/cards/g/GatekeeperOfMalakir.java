
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class GatekeeperOfMalakir extends CardImpl {

    private static final FilterControlledPermanent filter;

    static {
        filter = new FilterControlledPermanent("creature");
        filter.add(CardType.CREATURE.getPredicate());
    }

    public GatekeeperOfMalakir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {B} (You may pay an additional {B} as you cast this spell.)
        this.addAbility(new KickerAbility("{B}"));

        // When Gatekeeper of Malakir enters the battlefield, if it was kicked, target player sacrifices a creature.
        EntersBattlefieldTriggeredAbility ability =
                new EntersBattlefieldTriggeredAbility(new SacrificeEffect(filter, 1, "target player"));
        Ability conditionalAbility = new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, target player sacrifices a creature.");
        conditionalAbility.addTarget(new TargetPlayer());
        this.addAbility(conditionalAbility);
    }

    private GatekeeperOfMalakir(final GatekeeperOfMalakir card) {
        super(card);
    }

    @Override
    public GatekeeperOfMalakir copy() {
        return new GatekeeperOfMalakir(this);
    }
}
