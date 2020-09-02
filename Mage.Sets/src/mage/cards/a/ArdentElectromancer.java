package mage.cards.a;

import java.util.UUID;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class ArdentElectromancer extends CardImpl {

    public ArdentElectromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ardent Electromancer enters the battlefield, add {R} for each creature in your party.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DynamicManaEffect(Mana.RedMana(1), PartyCount.instance)
        ).addHint(PartyCountHint.instance));
    }

    private ArdentElectromancer(final ArdentElectromancer card) {
        super(card);
    }

    @Override
    public ArdentElectromancer copy() {
        return new ArdentElectromancer(this);
    }
}
