
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import static mage.cards.u.UnstableShapeshifter.filterAnotherCreature;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author HCrescent
 */
public final class UnstableShapeshifter extends CardImpl {

    static final FilterCreaturePermanent filterAnotherCreature = new FilterCreaturePermanent("another creature");

    static {
        filterAnotherCreature.add(AnotherPredicate.instance);
    }

    public UnstableShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever another creature enters the battlefield, Unstable Shapeshifter becomes a copy of that creature, except it has this ability.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UnstableShapeshifterEffect(), filterAnotherCreature, false, SetTargetPointer.PERMANENT));
    }

    private UnstableShapeshifter(final UnstableShapeshifter card) {
        super(card);
    }

    @Override
    public UnstableShapeshifter copy() {
        return new UnstableShapeshifter(this);
    }
}

class UnstableShapeshifterEffect extends OneShotEffect {

    public UnstableShapeshifterEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of that creature, except it has this ability";
    }

    private UnstableShapeshifterEffect(final UnstableShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public UnstableShapeshifterEffect copy() {
        return new UnstableShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (targetCreature != null && permanent != null) {
            Permanent blueprintPermanent = game.copyPermanent(Duration.Custom, targetCreature, permanent.getId(), source, new EmptyCopyApplier());
            blueprintPermanent.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD,
                    new UnstableShapeshifterEffect(), filterAnotherCreature, false, SetTargetPointer.PERMANENT), source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
