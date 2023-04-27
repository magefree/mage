package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishrasFoundry extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking Assembly-Worker");

    static {
        filter.add(SubType.ASSEMBLY_WORKER.getPredicate());
    }

    public MishrasFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}: Mishra's Foundry becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        2, 2,
                        "2/2 Assembly-Worker artifact creature",
                        SubType.ASSEMBLY_WORKER
                ).withType(CardType.ARTIFACT), "land", Duration.EndOfTurn
        ), new GenericManaCost(2)));

        // {1}, {T}: Target attacking Assembly-Worker gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(2, 2), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MishrasFoundry(final MishrasFoundry card) {
        super(card);
    }

    @Override
    public MishrasFoundry copy() {
        return new MishrasFoundry(this);
    }
}
