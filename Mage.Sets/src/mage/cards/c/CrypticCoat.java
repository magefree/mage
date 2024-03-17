package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.CloakAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author layornos
 */
public final class CrypticCoat extends CardImpl{

    public CrypticCoat(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        this.subtype.add(SubType.EQUIPMENT);

        // When Cryptic Coat enters the battlefield, cloak the top card of your library, then attach Cryptic Coat to it. (To cloak a card, put it onto the battlefield face down as a 2/2 creature with ward {2}. Turn it face up any time for its mana cost if it’s a creature card.)
        this.addAbility(new CloakAbility());

        // Equipped creature gets +1/+0 and can’t be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT)));

        // {1}{U}: Return Cryptic Coat to its owner’s hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{1}{U}")));
    }

    @Override
    public Card copy() {
        return null;
    }
}
