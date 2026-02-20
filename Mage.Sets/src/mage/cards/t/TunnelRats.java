package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TunnelRats extends CardImpl {

    public TunnelRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{B}: Return this card from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                new ManaCostsImpl<>("{4}{B}")
        ));
    }

    private TunnelRats(final TunnelRats card) {
        super(card);
    }

    @Override
    public TunnelRats copy() {
        return new TunnelRats(this);
    }
}
