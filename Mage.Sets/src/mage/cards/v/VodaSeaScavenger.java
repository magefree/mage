package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VodaSeaScavenger extends CardImpl {

    public VodaSeaScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Domain — When Voda Sea Scavenger enters the battlefield, look at the top card X cards of your library, where X is the number of basic land types among lands you control. You may put one of those cards on top of your library. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                DomainValue.REGULAR, 1, LookLibraryControllerEffect.PutCards.TOP_ANY,
                LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM, true
        )).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private VodaSeaScavenger(final VodaSeaScavenger card) {
        super(card);
    }

    @Override
    public VodaSeaScavenger copy() {
        return new VodaSeaScavenger(this);
    }
}
