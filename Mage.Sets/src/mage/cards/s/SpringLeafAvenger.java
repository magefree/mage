package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpringLeafAvenger extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public SpringLeafAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ninjutsu {3}{G}
        this.addAbility(new NinjutsuAbility("{3}{G}"));

        // Whenever Spring-Leaf Avenger deals combat damage to a player, return target permanent card from your graveyard to your hand.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnFromGraveyardToHandTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SpringLeafAvenger(final SpringLeafAvenger card) {
        super(card);
    }

    @Override
    public SpringLeafAvenger copy() {
        return new SpringLeafAvenger(this);
    }
}
