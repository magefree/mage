package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.Robot11HeroToken;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ShuriVibraniumTechnologist extends CardImpl {

    public ShuriVibraniumTechnologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Shuri enters, choose one --
        // * Create a 1/1 colorless Robot Hero artifact creature token with flying.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Robot11HeroToken()));

        // * Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability);
    }

    private ShuriVibraniumTechnologist(final ShuriVibraniumTechnologist card) {
        super(card);
    }

    @Override
    public ShuriVibraniumTechnologist copy() {
        return new ShuriVibraniumTechnologist(this);
    }
}
