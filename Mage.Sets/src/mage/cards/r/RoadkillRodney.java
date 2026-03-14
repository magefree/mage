package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.MutagenToken;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SquadAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RoadkillRodney extends CardImpl {

    public RoadkillRodney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Squad {3}
        this.addAbility(new SquadAbility(new ManaCostsImpl<>("{3}")));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever this creature deals combat damage to a player, create a Mutagen token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new MutagenToken()), false
        ));
    }

    private RoadkillRodney(final RoadkillRodney card) {
        super(card);
    }

    @Override
    public RoadkillRodney copy() {
        return new RoadkillRodney(this);
    }
}
