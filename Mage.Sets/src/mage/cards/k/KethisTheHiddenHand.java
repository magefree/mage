package mage.cards.k;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KethisTheHiddenHand extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary spells");
    private static final FilterCard filter2 = new FilterCard("legendary cards from your graveyard");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(SuperType.LEGENDARY.getPredicate());
    }

    public KethisTheHiddenHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Legendary spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Exile two legendary cards from your graveyard: Until end of turn, each legendary card in your graveyard gains "You may play this card from your graveyard."
        this.addAbility(new SimpleActivatedAbility(
                new KethisTheHiddenHandEffect(),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(2, filter2))
        ));

    }

    private KethisTheHiddenHand(final KethisTheHiddenHand card) {
        super(card);
    }

    @Override
    public KethisTheHiddenHand copy() {
        return new KethisTheHiddenHand(this);
    }
}

class KethisTheHiddenHandEffect extends ContinuousEffectImpl {

    KethisTheHiddenHandEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Until end of turn, each legendary card in your graveyard " +
                "gains \"You may play this card from your graveyard.\"";
    }

    private KethisTheHiddenHandEffect(final KethisTheHiddenHandEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!this.affectedObjectsSet) {
            return;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        player.getGraveyard()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card1 -> card1.isLegendary(game))
                .forEach(card -> affectedObjectList.add(new MageObjectReference(card, game)));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getGraveyard()
                .getCards(game)
                .stream()
                .filter(card -> affectedObjectList
                        .stream()
                        .anyMatch(mor -> mor.refersTo(card, game))
                ).forEach(card -> {
            Ability ability = new SimpleStaticAbility(
                    Zone.GRAVEYARD, new KethisTheHiddenHandGraveyardEffect()
            );
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        });
        return true;
    }

    @Override
    public KethisTheHiddenHandEffect copy() {
        return new KethisTheHiddenHandEffect(this);
    }
}

class KethisTheHiddenHandGraveyardEffect extends AsThoughEffectImpl {

    KethisTheHiddenHandGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may play this card from your graveyard";
    }

    private KethisTheHiddenHandGraveyardEffect(final KethisTheHiddenHandGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KethisTheHiddenHandGraveyardEffect copy() {
        return new KethisTheHiddenHandGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(source.getSourceId())
                && affectedControllerId.equals(source.getControllerId())
                && game.getCard(source.getSourceId()) != null
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}
