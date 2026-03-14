package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarlandKnightOfCornelia extends TransformingDoubleFacedCard {

    public GarlandKnightOfCornelia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{B}{R}",
                "Chaos, the Endless",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DEMON}, "BR"
        );

        // Garland, Knight of Cornelia
        this.getLeftHalfCard().setPT(3, 2);

        // Whenever you cast a noncreature spell, surveil 1.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {3}{B}{B}{R}{R}: Return this card from your graveyard to the battlefield transformed. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new GarlandKnightOfCorneliaEffect(), new ManaCostsImpl<>("{3}{B}{B}{R}{R}")
        ));

        // Chaos, the Endless
        this.getRightHalfCard().setPT(5, 5);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // When Chaos dies, put it on the bottom of its owner's library.
        this.getRightHalfCard().addAbility(new DiesSourceTriggeredAbility(new PutOnLibrarySourceEffect(
                false, "put it on the bottom of its owner's library"
        ), false));
    }

    private GarlandKnightOfCornelia(final GarlandKnightOfCornelia card) {
        super(card);
    }

    @Override
    public GarlandKnightOfCornelia copy() {
        return new GarlandKnightOfCornelia(this);
    }
}

class GarlandKnightOfCorneliaEffect extends OneShotEffect {

    GarlandKnightOfCorneliaEffect() {
        super(Outcome.Benefit);
        staticText = "return this card from your graveyard to the battlefield transformed";
    }

    private GarlandKnightOfCorneliaEffect(final GarlandKnightOfCorneliaEffect effect) {
        super(effect);
    }

    @Override
    public GarlandKnightOfCorneliaEffect copy() {
        return new GarlandKnightOfCorneliaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = Optional
                .ofNullable(source.getSourceObjectIfItStillExists(game))
                .filter(Card.class::isInstance)
                .map(Card.class::cast)
                .orElse(null);
        if (player == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
