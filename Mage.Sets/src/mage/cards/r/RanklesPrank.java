package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RanklesPrank extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public RanklesPrank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");


        // Choose one or more --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // * Each player discards two cards.
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(2, false));

        // * Each player loses 4 life.
        this.getSpellAbility().addMode(new Mode(new LoseLifeAllPlayersEffect(4)));

        // * Each player sacrifices two creatures.
        this.getSpellAbility().addMode(new Mode(new SacrificeAllEffect(2, filter)));
    }

    private RanklesPrank(final RanklesPrank card) {
        super(card);
    }

    @Override
    public RanklesPrank copy() {
        return new RanklesPrank(this);
    }
}
