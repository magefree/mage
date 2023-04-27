
package mage.cards.j;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

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

    JeskaiInfiltratorEffect(final JeskaiInfiltratorEffect effect) {
        super(effect);
    }

    @Override
    public JeskaiInfiltratorEffect copy() {
        return new JeskaiInfiltratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToManifest = new HashSet<>();
            cardsToManifest.add(source.getSourcePermanentIfItStillExists(game));
            cardsToManifest.add(controller.getLibrary().getFromTop(game));
            UUID exileId = UUID.randomUUID();
            controller.moveCardsToExile(cardsToManifest, source, game, false, exileId, "");
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            for (Card card : exileZone.getCards(game)) {
                card.setFaceDown(true, game);
            }
            game.fireUpdatePlayersEvent(); // removes Jeskai Infiltrator from Battlefield, so Jeskai Infiltrator returns as a fresh permanent to the battlefield with new position

            Ability newSource = source.copy();
            newSource.setWorksFaceDown(true);
            //the Set will mimic the Shuffling
            exileZone.getCards(game).forEach(card -> {
                ManaCosts manaCosts = null;
                if (card.isCreature(game)) {
                    manaCosts = card.getSpellAbility().getManaCosts();
                    if (manaCosts == null) {
                        manaCosts = new ManaCostsImpl<>("{0}");
                    }
                }
                MageObjectReference objectReference = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
                game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, FaceDownType.MANIFESTED), newSource);
            });
            controller.moveCards(exileZone.getCards(game), Zone.BATTLEFIELD, source, game, false, true, false, null);
            return true;
        }
        return false;
    }
}
