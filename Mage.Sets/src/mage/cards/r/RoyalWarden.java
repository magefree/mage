package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.NecronWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoyalWarden extends CardImpl {

    public RoyalWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Phalanx Commander -- When Royal Warden enters the battlefield, create two tapped 2/2 black Necron Warrior artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new NecronWarriorToken(), 2, true, false
        )).withFlavorWord("Phalanx Commander"));

        // Unearth {3}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private RoyalWarden(final RoyalWarden card) {
        super(card);
    }

    @Override
    public RoyalWarden copy() {
        return new RoyalWarden(this);
    }
}
