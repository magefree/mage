package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PonderingMage extends CardImpl {

    public PonderingMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Pondering Mage enters the battlefield, look at the top three cards of your library, then put them back in any order. You may shuffle your library. Draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(3));
        ability.addEffect(new ShuffleLibrarySourceEffect(true));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private PonderingMage(final PonderingMage card) {
        super(card);
    }

    @Override
    public PonderingMage copy() {
        return new PonderingMage(this);
    }
}
