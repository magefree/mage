package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class Ringskipper extends CardImpl {

    public Ringskipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When {this} is put into graveyard from play, clash with an opponent. If you win return {this} to its owner's hand
        this.addAbility(new DiesSourceTriggeredAbility(new DoIfClashWonEffect(new ReturnToHandSourceEffect(false,true))));
    }

    private Ringskipper(final Ringskipper card) {
        super(card);
    }

    @Override
    public Ringskipper copy() {
        return new Ringskipper(this);
    }
}
