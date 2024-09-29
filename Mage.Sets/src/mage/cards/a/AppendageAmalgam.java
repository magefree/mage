package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AppendageAmalgam extends CardImpl {

    public AppendageAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever Appendage Amalgam attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1)));
    }

    private AppendageAmalgam(final AppendageAmalgam card) {
        super(card);
    }

    @Override
    public AppendageAmalgam copy() {
        return new AppendageAmalgam(this);
    }
}
