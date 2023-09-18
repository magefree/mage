
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetArtifactPermanent;

/**
 * @author Loki
 */
public final class YukiOnna extends CardImpl {

    public YukiOnna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Yuki-Onna enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
        // Whenever you cast a Spirit or Arcane spell, you may return Yuki-Onna to its owner's hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ReturnToHandSourceEffect(true), StaticFilters.FILTER_SPIRIT_OR_ARCANE_CARD, true));
    }

    private YukiOnna(final YukiOnna card) {
        super(card);
    }

    @Override
    public YukiOnna copy() {
        return new YukiOnna(this);
    }
}
