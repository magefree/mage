package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OozeToken;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConvertToSlime extends CardImpl {

    public ConvertToSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}");

        // Destroy up to one target artifact, up to one target creature, and up to one target enchantment.
        // Delirium -- Then if there are four or more card types among cards in your graveyard, create an X/X green Ooze creature token, where X is the total mana value of permanents destroyed this way.
        this.getSpellAbility().addEffect(new ConvertToSlimeEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addTarget(new TargetEnchantmentPermanent(0, 1));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private ConvertToSlime(final ConvertToSlime card) {
        super(card);
    }

    @Override
    public ConvertToSlime copy() {
        return new ConvertToSlime(this);
    }
}

class ConvertToSlimeEffect extends OneShotEffect {

    ConvertToSlimeEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to one target artifact, up to one target creature, " +
                "and up to one target enchantment.<br>" + AbilityWord.DELIRIUM.formatWord() +
                "Then if there are four or more card types among cards in your graveyard, create an X/X green " +
                "Ooze creature token, where X is the total mana value of permanents destroyed this way.";
        this.setTargetPointer(new EachTargetPointer());
    }

    private ConvertToSlimeEffect(final ConvertToSlimeEffect effect) {
        super(effect);
    }

    @Override
    public ConvertToSlimeEffect copy() {
        return new ConvertToSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int total = 0;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.destroy(source, game)) {
                total += permanent.getManaValue();
            }
        }
        game.processAction();
        if (DeliriumCondition.instance.apply(game, source)) {
            new OozeToken(total, total).putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
