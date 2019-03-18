
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AmbushCommander extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.ELF, "an Elf");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("Forests you control");

    static {
        filter2.add(new SubtypePredicate(SubType.FOREST));
    }

    public AmbushCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forests you control are 1/1 green Elf creatures that are still lands.
        ContinuousEffect effect = new BecomesCreatureAllEffect(
                new CreatureToken(1, 1, "1/1 green Elf creature").withColor("G").withSubType(SubType.ELF),
                "lands", filter2, Duration.WhileOnBattlefield, true);
        effect.getDependencyTypes().add(DependencyType.BecomeForest);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // {1}{G}, Sacrifice an Elf: Target creature gets +3/+3 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public AmbushCommander(final AmbushCommander card) {
        super(card);
    }

    @Override
    public AmbushCommander copy() {
        return new AmbushCommander(this);
    }
}
