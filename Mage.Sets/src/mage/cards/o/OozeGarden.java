
package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class OozeGarden extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ooze creature");
    static{
        filter.add(Predicates.not(SubType.OOZE.getPredicate()));
    }
    public OozeGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // {1}{G}, Sacrifice a non-Ooze creature: Create an X/X green Ooze creature token, where X is the sacrificed creature's power. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new OozeGardenCreateTokenEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    private OozeGarden(final OozeGarden card) {
        super(card);
    }

    @Override
    public OozeGarden copy() {
        return new OozeGarden(this);
    }
}

class OozeGardenCreateTokenEffect extends OneShotEffect {

    public OozeGardenCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Ooze creature token, where X is the sacrificed creature's power";
    }

    public OozeGardenCreateTokenEffect(final OozeGardenCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public OozeGardenCreateTokenEffect copy() {
        return new OozeGardenCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = 0;
        for(Cost cost : source.getCosts()){
            if(cost instanceof SacrificeTargetCost){
                value = ((SacrificeTargetCost)cost).getPermanents().get(0).getPower().getValue();
            }
        }
        Token token = new OozeToken(value);
        token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }
}

class OozeToken extends TokenImpl {

    public OozeToken() {
        this(1);
    }

    public OozeToken(int x) {
        super("Ooze Token", "X/X green Ooze creature token, where X is the sacrificed creature's power");
        this.cardType.add(CardType.CREATURE);
        this.color.addColor(ObjectColor.GREEN);
        this.subtype.add(SubType.OOZE);
        this.toughness = new MageInt(x);
        this.power = new MageInt(x);
    }

    public OozeToken(final OozeToken token) {
        super(token);
    }

    public OozeToken copy() {
        return new OozeToken(this);
    }
}