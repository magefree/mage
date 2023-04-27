
package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CelestialColonnade extends CardImpl {

    public CelestialColonnade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Celestial Colonnade enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {U]
        this.addAbility(new BlueManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {3}{W}{U}: Until end of turn, Celestial Colonnade becomes a 4/4 white and blue Elemental creature with flying and vigilance. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 white and blue Elemental creature with flying and vigilance")
                        .withColor("WU")
                        .withSubType(SubType.ELEMENTAL)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(VigilanceAbility.getInstance()),
                "land", Duration.EndOfTurn), new ManaCostsImpl<>("{3}{W}{U}")));
    }

    private CelestialColonnade(final CelestialColonnade card) {
        super(card);
    }

    @Override
    public CelestialColonnade copy() {
        return new CelestialColonnade(this);
    }

}