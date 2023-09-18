
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class KiriOnna extends CardImpl {

    public KiriOnna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Kiri-Onna enters the battlefield, return target creature to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Whenever you cast a Spirit or Arcane spell, you may return Kiri-Onna to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ReturnToHandSourceEffect(true), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private KiriOnna(final KiriOnna card) {
        super(card);
    }

    @Override
    public KiriOnna copy() {
        return new KiriOnna(this);
    }
}
