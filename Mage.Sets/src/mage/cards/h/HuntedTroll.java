
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.game.permanent.token.FaerieToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class HuntedTroll extends CardImpl {

    public HuntedTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(8);
        this.toughness = new MageInt(4);

        // When Hunted Troll enters the battlefield, create four 1/1 blue Faerie creature tokens with flying under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new FaerieToken(), 4), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
        // {G}: Regenerate Hunted Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.G)));
    }

    private HuntedTroll(final HuntedTroll card) {
        super(card);
    }

    @Override
    public HuntedTroll copy() {
        return new HuntedTroll(this);
    }
}
