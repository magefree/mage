package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.BrainstormEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavalierOfGales extends CardImpl {

    public CavalierOfGales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Cavalier of Gales enters the battlefield, draw three cards, then put two cards from your hand on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrainstormEffect()));

        // When Cavalier of Gales dies, shuffle it into its owner's library, then scry 2.
        Ability ability = new DiesTriggeredAbility(new ShuffleIntoLibrarySourceEffect());
        ability.addEffect(new ScryEffect(2).concatBy(", then"));
        this.addAbility(ability);
    }

    private CavalierOfGales(final CavalierOfGales card) {
        super(card);
    }

    @Override
    public CavalierOfGales copy() {
        return new CavalierOfGales(this);
    }
}
