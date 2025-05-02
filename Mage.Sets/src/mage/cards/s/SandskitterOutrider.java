package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SandskitterOutrider extends CardImpl {

    public SandskitterOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When this creature enters, it endures 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EndureSourceEffect(2)));
    }

    private SandskitterOutrider(final SandskitterOutrider card) {
        super(card);
    }

    @Override
    public SandskitterOutrider copy() {
        return new SandskitterOutrider(this);
    }
}
