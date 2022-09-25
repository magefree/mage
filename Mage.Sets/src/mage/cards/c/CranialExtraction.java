package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class CranialExtraction extends CardImpl {

    public CranialExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.subtype.add(SubType.ARCANE);

        /* Choose a nonland card. Search target player's graveyard, hand, and library for
         * all cards with that name and exile them. Then that player shuffles their library. */
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CranialExtractionEffect());
    }

    private CranialExtraction(final CranialExtraction card) {
        super(card);
    }

    @Override
    public CranialExtraction copy() {
        return new CranialExtraction(this);
    }
}

class CranialExtractionEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    CranialExtractionEffect() {
        super(false, "target player's", "all cards with that name");
        this.staticText = "Choose a nonland card name. " + CardUtil.getTextWithFirstCharUpperCase(this.staticText);
    }

    CranialExtractionEffect(final CranialExtractionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return true;
        }
        String cardName = ChooseACardNameEffect.TypeOfName.NON_LAND_NAME.getChoice(player, game, source, false);
        if (cardName == null) {
            return false;
        }
        super.applySearchAndExile(game, source, cardName, player.getId());
        return true;
    }

    @Override
    public CranialExtractionEffect copy() {
        return new CranialExtractionEffect(this);
    }
}
