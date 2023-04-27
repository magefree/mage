
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author fireshoes
 */
public final class MirageMirror extends CardImpl {



    public MirageMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}: Mirage Mirror becomes a copy of target artifact, creature, enchantment, or land until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MirageMirrorCopyEffect(), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND));
        this.addAbility(ability);
    }

    private MirageMirror(final MirageMirror card) {
        super(card);
    }

    @Override
    public MirageMirror copy() {
        return new MirageMirror(this);
    }
}

class MirageMirrorCopyEffect extends OneShotEffect {

    public MirageMirrorCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of target artifact, creature, enchantment, or land until end of turn";
    }

    public MirageMirrorCopyEffect(final MirageMirrorCopyEffect effect) {
        super(effect);
    }

    @Override
    public MirageMirrorCopyEffect copy() {
        return new MirageMirrorCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            game.copyPermanent(Duration.EndOfTurn, copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            return true;
        }
        return false;
    }
}
