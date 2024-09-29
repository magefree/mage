package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author jimga150
 */
public final class SunfrillImitator extends CardImpl {

    public SunfrillImitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sunfrill Imitator attacks, you may have it become a copy of another target Dinosaur you control, 
        // except its name is Sunfrill Imitator and it has this ability.
        this.addAbility(new SunfrillImitatorAbility());
    }

    private SunfrillImitator(final SunfrillImitator card) {
        super(card);
    }

    @Override
    public SunfrillImitator copy() {
        return new SunfrillImitator(this);
    }
}

class SunfrillImitatorAbility extends AttacksTriggeredAbility {

    private static final FilterControlledPermanent dino_filter = new FilterControlledPermanent(SubType.DINOSAUR, "Dinosaur");

    static {
        dino_filter.add(AnotherPredicate.instance);
    }

    public SunfrillImitatorAbility() {
        super(new SunfrillImitatorEffect(), true);
        this.addTarget(new TargetControlledPermanent(dino_filter));
    }

    private SunfrillImitatorAbility(final SunfrillImitatorAbility ability) {
        super(ability);
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
            // Permanent also keeps this copy ability
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
