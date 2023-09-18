
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LevelX2
 */
public final class RudeAwakening extends CardImpl {

    public RudeAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Untap all lands you control;
        this.getSpellAbility().addEffect(new UntapAllLandsControllerEffect());
        // or until end of turn, lands you control become 2/2 creatures that are still lands.
        Mode mode = new Mode(new BecomesCreatureAllEffect(
                new CreatureToken(2, 2),
                "lands", new FilterControlledLandPermanent("lands you control"), Duration.EndOfTurn, false));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{G}
        this.addAbility(new EntwineAbility("{2}{G}"));
    }

    private RudeAwakening(final RudeAwakening card) {
        super(card);
    }

    @Override
    public RudeAwakening copy() {
        return new RudeAwakening(this);
    }
}