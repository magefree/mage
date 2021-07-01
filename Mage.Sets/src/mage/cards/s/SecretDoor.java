package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecretDoor extends CardImpl {

    public SecretDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {4}{U}: Venture into the dungeon. Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new VentureIntoTheDungeonEffect(), new ManaCostsImpl<>("{4}{U}")
        ));
    }

    private SecretDoor(final SecretDoor card) {
        super(card);
    }

    @Override
    public SecretDoor copy() {
        return new SecretDoor(this);
    }
}
