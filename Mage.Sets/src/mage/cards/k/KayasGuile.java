package mage.cards.k;

import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.WhiteBlackSpiritToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayasGuile extends CardImpl {

    public KayasGuile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{B}");

        // Choose two —
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Each opponent sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ));

        // • Exile all cards from each opponent's graveyard.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllPlayersEffect(
                StaticFilters.FILTER_CARD_CARDS, TargetController.OPPONENT
        )));

        // • Create a 1/1 white and black Spirit creature token with flying.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(new WhiteBlackSpiritToken())));

        // • You gain 4 life.
        this.getSpellAbility().addMode(new Mode(new GainLifeEffect(4)));

        // Entwine {3}
        this.addAbility(new EntwineAbility(new ManaCostsImpl<>("{3}"), "Choose all if you pay the entwine cost."));
    }

    private KayasGuile(final KayasGuile card) {
        super(card);
    }

    @Override
    public KayasGuile copy() {
        return new KayasGuile(this);
    }
}
