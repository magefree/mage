package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class FearOfSurveillance extends CardImpl {

    public FearOfSurveillance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Fear of Surveillance attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1)));
    }

    private FearOfSurveillance(final FearOfSurveillance card) {
        super(card);
    }

    @Override
    public FearOfSurveillance copy() {
        return new FearOfSurveillance(this);
    }
}
