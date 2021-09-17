package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class DennickPiousApparition extends CardImpl {

    public DennickPiousApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more creature cards are put into graveyards from anywhere, investigate. This ability triggers only once each turn.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(new InvestigateEffect(1), false, TargetController.ANY).setTriggersOnce(true));

        // If Dennick, Pious Apparition would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private DennickPiousApparition(final DennickPiousApparition card) {
        super(card);
    }

    @Override
    public DennickPiousApparition copy() {
        return new DennickPiousApparition(this);
    }
}
