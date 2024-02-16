

package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class BlinkmothNexus extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Blinkmoth creature");

    static {
        filter.add(SubType.BLINKMOTH.getPredicate());
    }

    public BlinkmothNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // {T}: Add {C}to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {1}: Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(1, 1, "1/1 Blinkmoth artifact creature with flying")
                        .withSubType(SubType.BLINKMOTH)
                        .withType(CardType.ARTIFACT)
                        .withAbility(FlyingAbility.getInstance()),
                CardType.LAND, Duration.EndOfTurn), new GenericManaCost(1)));

        // {1}, {T}: Target Blinkmoth creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private BlinkmothNexus(final BlinkmothNexus card) {
        super(card);
    }

    @Override
    public BlinkmothNexus copy() {
        return new BlinkmothNexus(this);
    }

}
