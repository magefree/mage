

package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author Loki
 */
public final class CreepingTarPit extends CardImpl {

    public CreepingTarPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Creeping Tar Pit enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());

        // {1}{U}{B}: Until end of turn, Creeping Tar Pit becomes a 3/2 blue and black Elemental creature and can't be blocked. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 2, "3/2 blue and black Elemental creature and can't be blocked")
                        .withColor("BU")
                        .withSubType(SubType.ELEMENTAL)
                        .withAbility(new CantBeBlockedSourceAbility()),
                "land", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}{B}")));
    }

    private CreepingTarPit(final CreepingTarPit card) {
        super(card);
    }

    @Override
    public CreepingTarPit copy() {
        return new CreepingTarPit(this);
    }

}
