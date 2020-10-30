
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class SandStrangler extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(SubType.DESERT.getPredicate());
        filterDesertCard.add(SubType.DESERT.getPredicate());
    }

    public SandStrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sand Strangler enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you may have Sand Strangler deal 3 damage to target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3), true),
                new OrCondition(
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveyardCondition(1, filterDesertCard)),
                "When {this} enters the battlefield, if you control a Desert or there is a Desert card in your graveyard, you may have {this} deal 3 damage to target creature.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public SandStrangler(final SandStrangler card) {
        super(card);
    }

    @Override
    public SandStrangler copy() {
        return new SandStrangler(this);
    }
}
