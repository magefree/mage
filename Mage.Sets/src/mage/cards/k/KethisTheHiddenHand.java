package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KethisTheHiddenHand extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary spells");
    private static final FilterCard filter2 = new FilterCard("legendary cards from your graveyard");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        filter2.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public KethisTheHiddenHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
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
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card == null || !card.isLegendary()) {
                continue;
            }
            Ability ability = new SimpleStaticAbility(
                    Zone.GRAVEYARD, new KethisTheHiddenHandGraveyardEffect()
            );
            ability.setSourceId(cardId);
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
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
