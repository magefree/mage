
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class NightfallPredator extends CardImpl {

    public NightfallPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        // {R}, {tap}: Nightfall Predator fights target creature.
        Ability activatedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NightfallPredatorEffect(), new ManaCostsImpl("{R}"));
        activatedAbility.addCost(new TapSourceCost());
        activatedAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(activatedAbility);
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Nightfall Predator.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public NightfallPredator(final NightfallPredator card) {
        super(card);
    }

    @Override
    public NightfallPredator copy() {
        return new NightfallPredator(this);
    }
}

class NightfallPredatorEffect extends OneShotEffect {

    public NightfallPredatorEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} fights target creature";
    }

    public NightfallPredatorEffect(final NightfallPredatorEffect effect) {
        super(effect);
    }

    @Override
    public NightfallPredatorEffect copy() {
        return new NightfallPredatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getSourceId());
        Permanent creature2 = game.getPermanent(source.getFirstTarget());
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null) {
            if (creature1.isCreature() && creature2.isCreature()) {
                return creature1.fight(creature2, source, game);
            }
        }
        return false;
    }
}
