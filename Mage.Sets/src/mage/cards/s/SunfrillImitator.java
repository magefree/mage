package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author anonymous
 */
public final class SunfrillImitator extends CardImpl {

    private static final FilterCreaturePermanent dino_filter =
            new FilterCreaturePermanent(SubType.DINOSAUR, "dinosaur");

    static {
        dino_filter.add(AnotherPredicate.instance);
    }

    public SunfrillImitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sunfrill Imitator attacks, you may have it become a copy of another target Dinosaur you control, 
        // except its name is Sunfrill Imitator and it has this ability.
        Ability ability = new SunfrillImitatorAbility();
        ability.addTarget(new TargetCreaturePermanent(dino_filter));
        this.addAbility(ability);
    }

    private SunfrillImitator(final SunfrillImitator card) {
        super(card);
    }

    @Override
    public SunfrillImitator copy() {
        return new SunfrillImitator(this);
    }
}

class SunfrillImitatorAbility extends TriggeredAbilityImpl {

    public SunfrillImitatorAbility() {
        super(Zone.BATTLEFIELD, null, true);
        this.addEffect(new SunfrillImitatorEffect());
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    private SunfrillImitatorAbility(final SunfrillImitatorAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public SunfrillImitatorAbility copy() {
        return new SunfrillImitatorAbility(this);
    }
}

class SunfrillImitatorEffect extends OneShotEffect {

    private static final CopyApplier copyApplier = new CopyApplier() {
        @Override
        public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
            blueprint.setName("Sunfrill Imitator");
            blueprint.getAbilities().add(new SunfrillImitatorAbility());
            return true;
        }
    };

    public SunfrillImitatorEffect() {
        super(Outcome.Benefit);
        staticText = "you may have it become a copy of another target Dinosaur you control, " +
                "except its name is Sunfrill Imitator and it has this ability.";
    }

    private SunfrillImitatorEffect(final SunfrillImitatorEffect effect) {
        super(effect);
    }

    // The following is based on Identity Thief and Cephalid Facetaker
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (targetPermanent != null && sourcePermanent != null) {
            game.copyPermanent(Duration.EndOfGame, targetPermanent, sourcePermanent.getId(), source, copyApplier);
            return true;
        }
        return false;
    }

    @Override
    public SunfrillImitatorEffect copy() {
        return new SunfrillImitatorEffect(this);
    }
}
