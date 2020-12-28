package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DwarfBerserkerToken;

/**
 *
 * @author weirddan455
 */
public final class WarchanterSkald extends CardImpl {

    public WarchanterSkald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Warchanter Skald becomes tapped, if it's enchanted or equipped, create a 2/1 red Dwarf Berserker creature token.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(
                new CreateTokenEffect(new DwarfBerserkerToken()), false,
                new OrCondition("if it's enchanted or equipped", new EnchantedSourceCondition(), EquippedSourceCondition.instance)
        ));
    }

    private WarchanterSkald(final WarchanterSkald card) {
        super(card);
    }

    @Override
    public WarchanterSkald copy() {
        return new WarchanterSkald(this);
    }
}
