package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.VillainToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MadameMasque extends CardImpl {

    public MadameMasque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Madame Masque enters, she connives.
        this.addAbility(
            new EntersBattlefieldTriggeredAbility(new ConniveSourceEffect("she"))
        );

        // Whenever you draw your second card each turn, create a 2/1 black Villain creature token with menace.
        this.addAbility(
            new DrawNthCardTriggeredAbility(new CreateTokenEffect(new VillainToken()))
        );
    }

    private MadameMasque(final MadameMasque card) {
        super(card);
    }

    @Override
    public MadameMasque copy() {
        return new MadameMasque(this);
    }
}
