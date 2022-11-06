package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class ForceProjection extends CardImpl {

    public ForceProjection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Create a token that is a copy of target creature you control except that it is an Illusion in addition to its other types and gains "When this creature becomes the target of a spell, sacrifice it."
        this.getSpellAbility().addEffect(new ForceProjectionEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Scry 2.
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private ForceProjection(final ForceProjection card) {
        super(card);
    }

    @Override
    public ForceProjection copy() {
        return new ForceProjection(this);
    }
}

class ForceProjectionEffect extends OneShotEffect {

    public ForceProjectionEffect() {
        super(Outcome.Copy);
        this.staticText = "Create a token that is a copy of target creature you control except that it is an Illusion "
                + "in addition to its other types and gains \"When this creature becomes the target of a spell, sacrifice it.\"";
    }

    public ForceProjectionEffect(final ForceProjectionEffect effect) {
        super(effect);
    }

    @Override
    public ForceProjectionEffect copy() {
        return new ForceProjectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && permanent != null) {
            // Create a token that is a copy of target creature
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
            effect.setTargetPointer(new FixedTarget(permanent, game));

            // except that it is an Illusion in addition to its other types
            effect.setAdditionalSubType(SubType.SPIRIT);

            // and gains "When this creature becomes the target of a spell, sacrifice it."
            effect.addAdditionalAbilities(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect(), new FilterSpell()));

            return effect.apply(game, source);
        }
        return false;
    }
}
