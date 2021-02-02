
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedCardsGraveyardsEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public final class UnderworldCerberus extends CardImpl {

    public UnderworldCerberus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Underworld Cerberus can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(3)));

        // Cards in graveyards can't be the targets of spells or abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedCardsGraveyardsEffect()));

        // When Underworld Cerberus dies, exile it and each player returns all creature cards from their graveyard to their hand.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect());
        ability.addEffect(new ReturnToHandFromGraveyardAllEffect(new FilterCreatureCard("creature cards")));
        this.addAbility(ability);
    }

    private UnderworldCerberus(final UnderworldCerberus card) {
        super(card);
    }

    @Override
    public UnderworldCerberus copy() {
        return new UnderworldCerberus(this);
    }
}
