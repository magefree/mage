package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.LolthSpiderToken;

/**
 *
 * @author weirddan455
 */
public final class Drider extends CardImpl {

    public Drider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Drider deals combat damage to a player, create a 2/1 black Spider creature token with menace and reach.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new LolthSpiderToken()), false));
    }

    private Drider(final Drider card) {
        super(card);
    }

    @Override
    public Drider copy() {
        return new Drider(this);
    }
}
