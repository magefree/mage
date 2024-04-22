package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OasisGardener extends CardImpl {

    public OasisGardener(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Oasis Gardener enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private OasisGardener(final OasisGardener card) {
        super(card);
    }

    @Override
    public OasisGardener copy() {
        return new OasisGardener(this);
    }
}
