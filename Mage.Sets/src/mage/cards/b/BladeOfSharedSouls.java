package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladeOfSharedSouls extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(BladeOfSharedSoulsPredicate.instance);
    }

    public BladeOfSharedSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        // Whenever Blade of Shared Souls becomes attached to a creature, for as long as Blade of Shared Souls remains attached to it, you may have that creature become a copy of another target creature you control.
        Ability ability = new AttachedToCreatureSourceTriggeredAbility(new BladeOfSharedSoulsEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private BladeOfSharedSouls(final BladeOfSharedSouls card) {
        super(card);
    }

    @Override
    public BladeOfSharedSouls copy() {
        return new BladeOfSharedSouls(this);
    }
}

enum BladeOfSharedSoulsPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getSource()
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("attachedPermanent"))
                .filter(Permanent.class::isInstance)
                .map(Permanent.class::cast)
                .noneMatch(permanent -> input.getObject().getId().equals(permanent.getId())
                        && input.getObject().getZoneChangeCounter(game) == permanent.getZoneChangeCounter(game));
    }
}

class BladeOfSharedSoulsEffect extends OneShotEffect {

    BladeOfSharedSoulsEffect() {
        super(Outcome.Benefit);
        staticText = "for as long as {this} remains attached to it, " +
                "you may have that creature become a copy of another target creature you control";
    }

    private BladeOfSharedSoulsEffect(final BladeOfSharedSoulsEffect effect) {
        super(effect);
    }

    @Override
    public BladeOfSharedSoulsEffect copy() {
        return new BladeOfSharedSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachedPermanent = (Permanent) getValue("attachedPermanent");
        Permanent copyCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (attachedPermanent == null
                || copyCreature == null
                || equipment == null
                || !equipment.isAttachedTo(attachedPermanent.getId())) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.chooseUse(outcome, "Copy the creature?", source, game)) {
            return false;
        }
        game.addEffect(new BladeOfSharedSoulsCopyEffect(copyCreature, attachedPermanent), source);
        return true;
    }
}

class BladeOfSharedSoulsCopyEffect extends CopyEffect {

    BladeOfSharedSoulsCopyEffect(Permanent copyCreature, Permanent attachedPermanent) {
        super(Duration.Custom, copyCreature, attachedPermanent.getId());
    }

    private BladeOfSharedSoulsCopyEffect(final BladeOfSharedSoulsCopyEffect effect) {
        super(effect);
    }

    @Override
    public BladeOfSharedSoulsCopyEffect copy() {
        return new BladeOfSharedSoulsCopyEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            return true;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !sourcePermanent.isAttachedTo(this.copyToObjectId)) {
            return true;
        }
        return false;
    }
}