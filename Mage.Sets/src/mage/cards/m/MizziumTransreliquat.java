
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;
import mage.util.functions.EmptyCopyApplier;

/**
 *
 * @author fireshoes
 */
public final class MizziumTransreliquat extends CardImpl {

    public MizziumTransreliquat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}: Mizzium Transreliquat becomes a copy of target artifact until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyEffect(), new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // {1}{U}{R}: Mizzium Transreliquat becomes a copy of target artifact, except it has this ability.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyAndGainAbilityEffect(), new ManaCostsImpl<>("{1}{U}{R}"));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private MizziumTransreliquat(final MizziumTransreliquat card) {
        super(card);
    }

    @Override
    public MizziumTransreliquat copy() {
        return new MizziumTransreliquat(this);
    }
}

class MizziumTransreliquatCopyEffect extends OneShotEffect {

    public MizziumTransreliquatCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "{this} becomes a copy of target artifact until end of turn";
    }

    public MizziumTransreliquatCopyEffect(final MizziumTransreliquatCopyEffect effect) {
        super(effect);
    }

    @Override
    public MizziumTransreliquatCopyEffect copy() {
        return new MizziumTransreliquatCopyEffect(this);
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

class MizziumTransreliquatCopyAndGainAbilityEffect extends OneShotEffect {

    public MizziumTransreliquatCopyAndGainAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} becomes a copy of target artifact, except it has this ability";
    }

    public MizziumTransreliquatCopyAndGainAbilityEffect(final MizziumTransreliquatCopyAndGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public MizziumTransreliquatCopyAndGainAbilityEffect copy() {
        return new MizziumTransreliquatCopyAndGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent copyFromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent != null && copyFromPermanent != null) {
            Permanent newPermanent = game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, new EmptyCopyApplier());
            Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MizziumTransreliquatCopyAndGainAbilityEffect(), new ManaCostsImpl<>("{1}{U}{R}"));
            ability.addTarget(new TargetArtifactPermanent());
            newPermanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
