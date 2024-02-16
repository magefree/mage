
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Omnibian extends CardImpl {

    public Omnibian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}{U}");
        this.subtype.add(SubType.FROG);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Target creature becomes a 3/3 Frog until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(
                new CreatureToken(3, 3, "Frog with base power and toughness 3/3", SubType.FROG),
                false, false, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Omnibian(final Omnibian card) {
        super(card);
    }

    @Override
    public Omnibian copy() {
        return new Omnibian(this);
    }
}
