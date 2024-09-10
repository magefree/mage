package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author emerald000
 */
public final class JeskaiInfiltrator extends CardImpl {

    public JeskaiInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Jeskai Infiltrator can't be blocked as long as you control no other creatures.
        Effect effect = new ConditionalRestrictionEffect(new CantBeBlockedSourceEffect(), new CreatureCountCondition(1, TargetController.YOU));
        effect.setText("{this} can't be blocked as long as you control no other creatures");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever Jeskai Infiltrator deals combat damage to a player, exile it and the top card of your library in a face-down pile, shuffle that pile, then manifest those cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new JeskaiInfiltratorEffect(), false));
    }

    private JeskaiInfiltrator(final JeskaiInfiltrator card) {
        super(card);
    }

    @Override
    public JeskaiInfiltrator copy() {
        return new JeskaiInfiltrator(this);
    }
}

class JeskaiInfiltratorEffect extends OneShotEffect {

    JeskaiInfiltratorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile it and the top card of your library in a face-down pile, shuffle that pile, then manifest those cards";
    }

    private JeskaiInfiltratorEffect(final JeskaiInfiltratorEffect effect) {
        super(effect);
    }

    @Override
    public JeskaiInfiltratorEffect copy() {
        return new JeskaiInfiltratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        UUID exileId = UUID.randomUUID();
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            controller.moveCardsToExile(sourcePermanent, source, game, false, exileId, "");
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard != null) {
            controller.moveCardsToExile(topCard, source, game, false, exileId, "");
        }
        // need to get source permanent as card rather than permanent for next steps, hence this convoluted code
        List<Card> cardsToManifest = new ArrayList<>(game.getExile().getExileZone(exileId).getCards(game));
        if (cardsToManifest.isEmpty()) {
            return false;
        }
        for (Card card : cardsToManifest) {
            card.setFaceDown(true, game);
        }
        Collections.shuffle(cardsToManifest);
        game.informPlayers(controller.getLogName() + " shuffles the face-down pile");
        game.processAction();
        ManifestEffect.doManifestCards(game, source, controller, new LinkedHashSet<>(cardsToManifest));
        return true;
    }
}
