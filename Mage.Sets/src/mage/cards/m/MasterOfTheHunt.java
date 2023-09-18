package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolvesOfTheHuntToken;

import java.util.UUID;

/**
 * @author L_J
 */
public final class MasterOfTheHunt extends CardImpl {

    public MasterOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Create a 1/1 green Wolf creature token named Wolves of the Hunt. It has “bands with other creatures named Wolves of the Hunt.”
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new WolvesOfTheHuntToken()), new ManaCostsImpl<>("{2}{G}{G}")
        ));
    }

    private MasterOfTheHunt(final MasterOfTheHunt card) {
        super(card);
    }

    @Override
    public MasterOfTheHunt copy() {
        return new MasterOfTheHunt(this);
    }

}
