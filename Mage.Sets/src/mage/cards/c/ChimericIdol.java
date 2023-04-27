
package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author LevelX2
 */
public final class ChimericIdol extends CardImpl {

    public ChimericIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {0}: Tap all lands you control. Chimeric Idol becomes a 3/3 Turtle artifact creature until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TapAllEffect(new FilterControlledLandPermanent("lands you control")), new ManaCostsImpl<>("{0}"));
        ability.addEffect(new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 Turtle artifact creature")
                        .withSubType(SubType.TURTLE)
                        .withType(CardType.ARTIFACT),
                "", Duration.EndOfTurn));
        this.addAbility(ability);

    }

    private ChimericIdol(final ChimericIdol card) {
        super(card);
    }

    @Override
    public ChimericIdol copy() {
        return new ChimericIdol(this);
    }
}