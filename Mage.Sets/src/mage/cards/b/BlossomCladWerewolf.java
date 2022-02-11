package mage.cards.b;

import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlossomCladWerewolf extends CardImpl {

    public BlossomCladWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setGreen(true);
        this.nightCard = true;

        // {T}: Add two mana of any one color.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private BlossomCladWerewolf(final BlossomCladWerewolf card) {
        super(card);
    }

    @Override
    public BlossomCladWerewolf copy() {
        return new BlossomCladWerewolf(this);
    }
}
