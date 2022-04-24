package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DevilToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PugnaciousPugilist extends CardImpl {

    public PugnaciousPugilist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Pugnacious Pugilist attacks, create a tapped and attacking 1/1 red Devil creature token with "When this creature dies, it deals 1 damage to any target."
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(
                new DevilToken(), 1, true, true
        )));

        // Blitz {3}{R}
        this.addAbility(new BlitzAbility(this, "{3}{R}"));
    }

    private PugnaciousPugilist(final PugnaciousPugilist card) {
        super(card);
    }

    @Override
    public PugnaciousPugilist copy() {
        return new PugnaciousPugilist(this);
    }
}
