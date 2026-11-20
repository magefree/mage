package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class GeordiLaForgeInnovator extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Spacecraft");

    static {
        filter.add(SubType.SPACECRAFT.getPredicate());
    }

    public GeordiLaForgeInnovator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Geordi La Forge can't be blocked if you control a Spacecraft.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
            new CantBeBlockedSourceEffect(),
            new PermanentsOnTheBattlefieldCondition(filter),
            "{this} can't be blocked if you control a Spacecraft"
        )));

        // Whenever Geordi La Forge deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private GeordiLaForgeInnovator(final GeordiLaForgeInnovator card) {
        super(card);
    }

    @Override
    public GeordiLaForgeInnovator copy() {
        return new GeordiLaForgeInnovator(this);
    }
}
