package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.SoldierRedToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationArchers extends CardImpl {

    public FireNationArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {5}: This creature deals 2 damage to each opponent. Create a 2/2 red Soldier creature token.
        Ability ability = new SimpleActivatedAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), new GenericManaCost(5));
        ability.addEffect(new CreateTokenEffect(new SoldierRedToken()));
        this.addAbility(ability);
    }

    private FireNationArchers(final FireNationArchers card) {
        super(card);
    }

    @Override
    public FireNationArchers copy() {
        return new FireNationArchers(this);
    }
}
