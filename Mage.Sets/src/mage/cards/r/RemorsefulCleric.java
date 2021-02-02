package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class RemorsefulCleric extends CardImpl {

    public RemorsefulCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice Contrite Cleric: Exile all cards in target player's graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileGraveyardAllTargetPlayerEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RemorsefulCleric(final RemorsefulCleric card) {
        super(card);
    }

    @Override
    public RemorsefulCleric copy() {
        return new RemorsefulCleric(this);
    }
}
