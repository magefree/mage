package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.game.permanent.token.NewPlanetToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SaurianExplorer extends CardImpl {

    public SaurianExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, this creature gets +2/+2 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn)));

        // {6}: Create a tapped Planet land token named New Planet with "{T}: Add one mana of any color." Activate only as a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(
            new CreateTokenEffect(new NewPlanetToken(), 1, true),
            new ManaCostsImpl<>("{6}")
        ));
    }

    private SaurianExplorer(final SaurianExplorer card) {
        super(card);
    }

    @Override
    public SaurianExplorer copy() {
        return new SaurianExplorer(this);
    }
}
