package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IlluminatorVirtuoso extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public IlluminatorVirtuoso(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Illuminator Virtuoso becomes the target of a spell you control, it connives.
        this.addAbility(new BecomesTargetTriggeredAbility(
                new ConniveSourceEffect(), filter
        ).setTriggerPhrase("Whenever {this} becomes the target of a spell you control, "));
    }

    private IlluminatorVirtuoso(final IlluminatorVirtuoso card) {
        super(card);
    }

    @Override
    public IlluminatorVirtuoso copy() {
        return new IlluminatorVirtuoso(this);
    }
}
