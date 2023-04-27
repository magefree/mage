package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.RevealAndShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author North
 */
public final class Progenitus extends CardImpl {

    public Progenitus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HYDRA);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Protection from everything
        this.addAbility(new ProtectionFromEverythingAbility());

        // If Progenitus would be put into a graveyard from anywhere, reveal Progenitus and shuffle it into its owner's library instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new RevealAndShuffleIntoLibrarySourceEffect()));
    }

    private Progenitus(final Progenitus card) {
        super(card);
    }

    @Override
    public Progenitus copy() {
        return new Progenitus(this);
    }
}
