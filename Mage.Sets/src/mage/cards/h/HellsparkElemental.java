

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.events.GameEvent.EventType;


/**
 * @author BetaSteward_at_googlemail.com
 */
public final class HellsparkElemental extends CardImpl {

    public HellsparkElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample, haste
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, sacrifice Hellspark Elemental.
        this.addAbility(new OnEventTriggeredAbility(EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect()));

        // Unearth {1}{R}: Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{R}")));
    }

    private HellsparkElemental(final HellsparkElemental card) {
        super(card);
    }

    @Override
    public HellsparkElemental copy() {
        return new HellsparkElemental(this);
    }

}
