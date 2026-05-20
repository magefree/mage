package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.Inkling11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EternalStudent extends CardImpl {

    public EternalStudent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {1}{B}, Exile this card from your graveyard: Create two 1/1 white and black Inkling creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new Inkling11Token(), 2), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private EternalStudent(final EternalStudent card) {
        super(card);
    }

    @Override
    public EternalStudent copy() {
        return new EternalStudent(this);
    }
}
