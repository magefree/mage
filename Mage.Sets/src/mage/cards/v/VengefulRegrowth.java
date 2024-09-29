package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.token.PlantWarriorToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class VengefulRegrowth extends CardImpl {

    private static final FilterCard filter = new FilterLandCard("land cards from your graveyard");

    public VengefulRegrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Return up to three target land cards from your graveyard to the battlefield tapped. Create that many 4/2 green Plant Warrior creature tokens with reach.
        this.getSpellAbility().addEffect(new VengefulRegrowthEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, filter));

        // Flashback {6}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{G}{G}")));
    }

    private VengefulRegrowth(final VengefulRegrowth card) {
        super(card);
    }

    @Override
    public VengefulRegrowth copy() {
        return new VengefulRegrowth(this);
    }
}

class VengefulRegrowthEffect extends OneShotEffect {

    VengefulRegrowthEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Return up to three target land cards from your graveyard to the battlefield tapped. "
                + "Create that many 4/2 green Plant Warrior creature tokens with reach.";
    }

    private VengefulRegrowthEffect(final VengefulRegrowthEffect effect) {
        super(effect);
    }

    @Override
    public VengefulRegrowthEffect copy() {
        return new VengefulRegrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(
                getTargetPointer()
                        .getTargets(game, source)
                        .stream()
                        .map(game::getCard)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
        if (cards.isEmpty()) {
            return false;
        }

        controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);

        // Release note:
        //
        // The number of Plant Warrior tokens created is equal to the number of land cards returned
        // from your graveyard to the battlefield by Vengeful Regrowth. For example, if you cast
        // Vengeful Regrowth targeting three Forest cards in your graveyard but two of those Forest
        // cards are exiled in response, you'll return the remaining Forest card and create one Plant Warrior token.
        cards.retainZone(Zone.BATTLEFIELD, game);
        int returned = cards.size();
        if (returned > 0) {
            new CreateTokenEffect(new PlantWarriorToken(), returned)
                    .apply(game, source);
        }
        return true;
    }

}