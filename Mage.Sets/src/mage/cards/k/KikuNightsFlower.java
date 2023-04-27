

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX
 */
public final class KikuNightsFlower extends CardImpl {

    public KikuNightsFlower (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}{B}, {T}: Target creature deals damage to itself equal to its power.
        Ability ability;
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new KikuNightsFlowerEffect(), 
                new ManaCostsImpl<>("{2}{B}{B}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public KikuNightsFlower (final KikuNightsFlower card) {
        super(card);
    }

    @Override
    public KikuNightsFlower copy() {
        return new KikuNightsFlower(this);
    }
}

class KikuNightsFlowerEffect extends OneShotEffect {

        public KikuNightsFlowerEffect() {
            super(Outcome.Damage);
            this.staticText = "Target creature deals damage to itself equal to its power";        
        }

    public KikuNightsFlowerEffect(final KikuNightsFlowerEffect effect) {
        super(effect);
    }

    @Override
    public KikuNightsFlowerEffect copy() {
        return new KikuNightsFlowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
            return true;
        }
        return false;
    }
}

