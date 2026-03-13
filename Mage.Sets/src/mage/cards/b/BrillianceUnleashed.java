package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class BrillianceUnleashed extends CardImpl {

    public BrillianceUnleashed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Brilliance Unleashed deals 5 damage to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));

        // * Choose target artifact card in your graveyard.
        // Return it to the battlefield if it's an artifact creature card.
        // Otherwise, return it to the battlefield and it's a 3/3 Robot artifact creature with flying.
        Mode mode2 = new Mode(new BrillianceUnleashedEffect());
        mode2.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT));
        this.getSpellAbility().addMode(mode2);
    }

    private BrillianceUnleashed(final BrillianceUnleashed card) {
        super(card);
    }

    @Override
    public BrillianceUnleashed copy() {
        return new BrillianceUnleashed(this);
    }
}

class BrillianceUnleashedEffect extends OneShotEffect {

    BrillianceUnleashedEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target artifact card in your graveyard. " +
                "Return it to the battlefield if it's an artifact creature card. " +
                "Otherwise, return it to the battlefield and it's a 3/3 Robot artifact creature with flying.";
    }

    private BrillianceUnleashedEffect(final BrillianceUnleashedEffect effect) {
        super(effect);
    }

    @Override
    public BrillianceUnleashedEffect copy() {
        return new BrillianceUnleashedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }

        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        if (!card.isCreature(game)) {
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
            if (permanent != null) {
                game.addEffect(new BecomesCreatureTargetEffect(
                    new CreatureToken(3, 3, "3/3 Robot artifact creature with flying", SubType.ROBOT)
                        .withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()),
                    false, true, Duration.Custom
                ).setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }
        return true;
    }
}
