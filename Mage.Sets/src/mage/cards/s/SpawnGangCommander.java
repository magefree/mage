package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.EldraziSpawnToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpawnGangCommander extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ELDRAZI, "an Eldrazi");

    public SpawnGangCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, create three 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 3)));

        // {1}{C}, Sacrifice an Eldrazi: Spawn-Gang Commander deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new ManaCostsImpl<>("{1}{C}"));
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SpawnGangCommander(final SpawnGangCommander card) {
        super(card);
    }

    @Override
    public SpawnGangCommander copy() {
        return new SpawnGangCommander(this);
    }
}
