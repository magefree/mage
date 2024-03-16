package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000 & L_J
 */
public final class KnowledgeVault extends CardImpl {

    public KnowledgeVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, {T}: Exile the top card of your library face down.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileCardsFromTopOfLibraryControllerEffect(1, true, true),
                new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {0}: Sacrifice Knowledge Vault. If you do, discard your hand, then put all cards exiled with Knowledge Vault into their owner’s hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KnowledgeVaultReturnEffect(), new GenericManaCost(0)));

        // When Knowledge Vault leaves the battlefield, put all cards exiled with Knowledge Vault into their owner’s graveyard.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(
                new ReturnFromExileForSourceEffect(Zone.GRAVEYARD).withText(true, false, true),
                false));
    }

    private KnowledgeVault(final KnowledgeVault card) {
        super(card);
    }

    @Override
    public KnowledgeVault copy() {
        return new KnowledgeVault(this);
    }
}

class KnowledgeVaultReturnEffect extends OneShotEffect {

    KnowledgeVaultReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Sacrifice {this}. If you do, discard your hand, then put all cards exiled with {this} into their owner's hand";
    }

    private KnowledgeVaultReturnEffect(final KnowledgeVaultReturnEffect effect) {
        super(effect);
    }

    @Override
    public KnowledgeVaultReturnEffect copy() {
        return new KnowledgeVaultReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && controller != null) {
            if (sourcePermanent.sacrifice(source, game)) {
                new DiscardHandControllerEffect().apply(game, source);
                new ReturnFromExileForSourceEffect(Zone.HAND).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
