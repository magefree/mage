package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.KithkinSoldierToken;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PatrolSignaler extends CardImpl {

    public PatrolSignaler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, {untap}: Create a 1/1 white Kithkin Soldier creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new KithkinSoldierToken()), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);
    }

    private PatrolSignaler(final PatrolSignaler card) {
        super(card);
    }

    @Override
    public PatrolSignaler copy() {
        return new PatrolSignaler(this);
    }
}
