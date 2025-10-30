package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MutavaultToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MutableExplorer extends CardImpl {

    public MutableExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When this creature enters, create a tapped Mutavault token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new MutavaultToken(), 1, true)
        ));
    }

    private MutableExplorer(final MutableExplorer card) {
        super(card);
    }

    @Override
    public MutableExplorer copy() {
        return new MutableExplorer(this);
    }
}
