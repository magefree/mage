
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ScavengeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class DeadbridgeGoliath extends CardImpl {

    public DeadbridgeGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Scavenge {4}{G}{G} ({4}{G}{G} , Exile this card from your graveyard: Put a number of +1/+1 counter's equal to this card's power on target creature. Scavenge only as a sorcery.)
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{4}{G}{G}")));
    }

    private DeadbridgeGoliath(final DeadbridgeGoliath card) {
        super(card);
    }

    @Override
    public DeadbridgeGoliath copy() {
        return new DeadbridgeGoliath(this);
    }
}
