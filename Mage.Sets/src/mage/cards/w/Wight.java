package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author weirddan455
 */
public final class Wight extends CardImpl {

    public Wight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Wight enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Life Drain — Whenever a creature dealt damage by Wight this turn dies, create a tapped 2/2 black Zombie creature token and exile that card.
        Ability ability = new DealtDamageAndDiedTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 1, true, false));
        ability.addEffect(new ExileTargetEffect("and exile that card"));
        this.addAbility(ability.withFlavorWord("Life Drain"));
    }

    private Wight(final Wight card) {
        super(card);
    }

    @Override
    public Wight copy() {
        return new Wight(this);
    }
}
