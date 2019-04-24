
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesToughness;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KorozdaGuildmage extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a nontoken creature");

    static {
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public KorozdaGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{B}{G}: Target creature gets +1/+1 and gains intimidate until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1,1, Duration.EndOfTurn),new ManaCostsImpl("{1}{B}{G}"));
        ability.addEffect(new GainAbilityTargetEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}{B}{G}, Sacrifice a nontoken creature: create X 1/1 green Saproling creature tokens, where X is the sacrificed creature's toughness.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken(),new SacrificeCostCreaturesToughness()),new ManaCostsImpl("{2}{B}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filter, true)));
        this.addAbility(ability);
        
    }

    public KorozdaGuildmage(final KorozdaGuildmage card) {
        super(card);
    }

    @Override
    public KorozdaGuildmage copy() {
        return new KorozdaGuildmage(this);
    }
}