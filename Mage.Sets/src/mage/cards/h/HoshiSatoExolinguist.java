package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.FederationCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class HoshiSatoExolinguist extends CardImpl {

    public HoshiSatoExolinguist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Federation -- When Hoshi Sato enters, look at the top X cards of your library, where X is the number of creature types among non-Borg creatures you control. Put one of them into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
            FederationCount.instance, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )).setAbilityWord(AbilityWord.FEDERATION).addHint(FederationCount.instance.getHint());
        this.addAbility(ability);
    }

    private HoshiSatoExolinguist(final HoshiSatoExolinguist card) {
        super(card);
    }

    @Override
    public HoshiSatoExolinguist copy() {
        return new HoshiSatoExolinguist(this);
    }
}
