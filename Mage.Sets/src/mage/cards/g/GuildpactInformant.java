package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildpactInformant extends CardImpl {

    public GuildpactInformant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Guildpact Informant deals combat damage to a player or planeswalker,
        // proliferate. (Choose any number of permanents and/or players, then give each another counter of each kind already there.)
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(new ProliferateEffect(), false));
    }

    private GuildpactInformant(final GuildpactInformant card) {
        super(card);
    }

    @Override
    public GuildpactInformant copy() {
        return new GuildpactInformant(this);
    }
}
