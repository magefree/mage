
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Cryptbreaker extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Zombies you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public Cryptbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}, {T}, Discard a card: Create a 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

        // Tap three untapped Zombies you control: You draw a card and you lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1);
        effect.setText("You draw a card");
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true)));
        effect = new LoseLifeSourceControllerEffect(1);
        effect.setText("and you lose 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Cryptbreaker(final Cryptbreaker card) {
        super(card);
    }

    @Override
    public Cryptbreaker copy() {
        return new Cryptbreaker(this);
    }
}
