package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class IllusoryDemon extends CardImpl {

    public IllusoryDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When you cast a spell, sacrifice Illusory Demon.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SacrificeSourceEffect(), false).setTriggerPhrase("When you cast a spell, "));
    }

    private IllusoryDemon(final IllusoryDemon card) {
        super(card);
    }

    @Override
    public IllusoryDemon copy() {
        return new IllusoryDemon(this);
    }
}
