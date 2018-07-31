
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NoSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class LambholtPacifist extends CardImpl {

    public LambholtPacifist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.transformable = true;
        this.secondSideCardClazz = LambholtButcher.class;

        // Lambholt Pacifist can't attack unless you control a creature with power 4 or greater.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LambholtPacifistEffect()));

        // At the beginning of each upkeep, if no spells were cast last turn, transform Lambholt Pacifist.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(true), TargetController.ANY, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, NoSpellsWereCastLastTurnCondition.instance, TransformAbility.NO_SPELLS_TRANSFORM_RULE));
    }

    public LambholtPacifist(final LambholtPacifist card) {
        super(card);
    }

    @Override
    public LambholtPacifist copy() {
        return new LambholtPacifist(this);
    }
}

class LambholtPacifistEffect extends RestrictionEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public LambholtPacifistEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack unless you control a creature with power 4 or greater";
    }

    public LambholtPacifistEffect(final LambholtPacifistEffect effect) {
        super(effect);
    }

    @Override
    public LambholtPacifistEffect copy() {
        return new LambholtPacifistEffect(this);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            if (game.getBattlefield().countAll(filter, source.getControllerId(), game) > 0) {
                return false;
            }
            return true;
        }  // do not apply to other creatures.
        return false;
    }
}

