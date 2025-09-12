package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DwarfBerserkerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarchanterSkald extends CardImpl {

    private static final Condition condition = new OrCondition(
            "it's enchanted or equipped",
            new EnchantedSourceCondition(),
            EquippedSourceCondition.instance
    );

    public WarchanterSkald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Warchanter Skald becomes tapped, if it's enchanted or equipped, create a 2/1 red Dwarf Berserker creature token.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new CreateTokenEffect(new DwarfBerserkerToken())).withInterveningIf(condition));
    }

    private WarchanterSkald(final WarchanterSkald card) {
        super(card);
    }

    @Override
    public WarchanterSkald copy() {
        return new WarchanterSkald(this);
    }
}
