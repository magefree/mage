
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.EternalizeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class EarthshakerKhenra extends CardImpl {

    private final UUID originalId;
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power less than or equal to {this}'s power");

    public EarthshakerKhenra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Earthshaker Khenra enters the battlefield, target creature with power less than or equal to Earthshaker Khenra's power can't block this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthshakerKhenraEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        originalId = ability.getOriginalId();

        // Eternalize {4}{R}{R}
        this.addAbility(new EternalizeAbility(new ManaCostsImpl("{4}{R}{R}"), this));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null) {
                FilterCreaturePermanent targetFilter = new FilterCreaturePermanent("creature with power less than or equal to " + getLogName() + "'s power");
                targetFilter.add(new PowerPredicate(ComparisonType.FEWER_THAN, sourcePermanent.getPower().getValue() + 1));
                ability.getTargets().clear();
                ability.getTargets().add(new TargetCreaturePermanent(targetFilter));
            }
        }
    }

    public EarthshakerKhenra(final EarthshakerKhenra card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public EarthshakerKhenra copy() {
        return new EarthshakerKhenra(this);
    }
}

class EarthshakerKhenraEffect extends OneShotEffect {

    public EarthshakerKhenraEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "target creature with power less than or equal to {this}'s power can't block this turn";
    }

    public EarthshakerKhenraEffect(final EarthshakerKhenraEffect effect) {
        super(effect);
    }

    @Override
    public EarthshakerKhenraEffect copy() {
        return new EarthshakerKhenraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            /*
            27.06.2017 	The target creature's power is checked when you target it with Earthshaker Khenra's ability
                        and when that ability resolves. Once the ability resolves, if the creature's power increases
                        or Earthshaker Khenra's power decreases, the target creature will still be unable to block.
             */
            if (targetCreature != null && targetCreature.getPower().getValue() <= sourceObject.getPower().getValue()) {
                ContinuousEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(targetCreature, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
