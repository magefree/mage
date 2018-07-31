
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.CardsInControllerGraveCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author spjspj
 */
public final class WretchedCamel extends CardImpl {

    private static final FilterControlledPermanent filterDesertPermanent = new FilterControlledPermanent("Desert");
    private static final FilterCard filterDesertCard = new FilterCard("Desert card");

    static {
        filterDesertPermanent.add(new SubtypePredicate(SubType.DESERT));
        filterDesertCard.add(new SubtypePredicate(SubType.DESERT));
    }

    public WretchedCamel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Wretched Camel dies, if you control a Desert or there is a Desert card in your graveyard, target player discards a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DiesTriggeredAbility(new DiscardTargetEffect(1)),
                new OrCondition(
                        new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(filterDesertPermanent)),
                        new CardsInControllerGraveCondition(1, filterDesertCard)),
                "When {this} dies, if you control a Desert or there is a Desert card in your graveyard, target player discards a card.");

        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public WretchedCamel(final WretchedCamel card) {
        super(card);
    }

    @Override
    public WretchedCamel copy() {
        return new WretchedCamel(this);
    }
}
