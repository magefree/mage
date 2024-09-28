package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToralfsDisciple extends CardImpl {

    public ToralfsDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Toralf's Disciple attacks, conjure four cards named Lightning Bolt into your library, then shuffle.
        Ability ability = new AttacksTriggeredAbility(new ConjureCardEffect(
                "Lightning Bolt", Zone.LIBRARY, 4
        ));
        ability.addEffect(new ShuffleLibrarySourceEffect().setText(", then shuffle"));
        this.addAbility(ability);
    }

    private ToralfsDisciple(final ToralfsDisciple card) {
        super(card);
    }

    @Override
    public ToralfsDisciple copy() {
        return new ToralfsDisciple(this);
    }
}
