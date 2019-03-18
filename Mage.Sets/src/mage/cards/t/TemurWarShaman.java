
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.TurnedFaceUpAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TemurWarShaman extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public TemurWarShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Temur War Shaman enters the battlefield, manifest the top card of your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ManifestEffect(1), false));

        // Whenever a permanent you control is turned face up, if it is a creature, you may have it fight target creature you don't control.
        Ability ability = new TemurWarShamanTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public TemurWarShaman(final TemurWarShaman card) {
        super(card);
    }

    @Override
    public TemurWarShaman copy() {
        return new TemurWarShaman(this);
    }
}

class TemurWarShamanTriggeredAbility extends TurnedFaceUpAllTriggeredAbility {

    public TemurWarShamanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TemurWarShamanFightEffect(), new FilterControlledCreaturePermanent(), true, true);
    }

    public TemurWarShamanTriggeredAbility(final TemurWarShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TemurWarShamanTriggeredAbility copy() {
        return new TemurWarShamanTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a permanent you control is turned face up, if it is a creature, you may have it fight target creature you don't control.";
    }
}

class TemurWarShamanFightEffect extends OneShotEffect {

    TemurWarShamanFightEffect() {
        super(Outcome.Damage);
    }

    TemurWarShamanFightEffect(final TemurWarShamanFightEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature()
                && target.isCreature()) {
            triggeredCreature.fight(target, source, game);
            return true;
        }
        return false;
    }

    @Override
    public TemurWarShamanFightEffect copy() {
        return new TemurWarShamanFightEffect(this);
    }
}
