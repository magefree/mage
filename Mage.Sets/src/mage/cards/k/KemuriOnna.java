
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class KemuriOnna extends CardImpl {

    public KemuriOnna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Kemuri-Onna enters the battlefield, target player discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Whenever you cast a Spirit or Arcane spell, you may return Kemuri-Onna to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ReturnToHandSourceEffect(true), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private KemuriOnna(final KemuriOnna card) {
        super(card);
    }

    @Override
    public KemuriOnna copy() {
        return new KemuriOnna(this);
    }
}
