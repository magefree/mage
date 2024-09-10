package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieToken;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gravedig extends CardImpl {

    public Gravedig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one --
        // * Target player creates a 2/2 black Zombie creature token.
        this.getSpellAbility().addEffect(new CreateTokenTargetEffect(new ZombieToken()));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private Gravedig(final Gravedig card) {
        super(card);
    }

    @Override
    public Gravedig copy() {
        return new Gravedig(this);
    }
}
