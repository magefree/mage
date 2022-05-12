
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.continuous.BecomesChosenCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class TrickeryCharm extends CardImpl {

    public TrickeryCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one - Target creature gains flying until end of turn
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or target creature becomes the creature type of your choice until end of turn
        Mode mode = new Mode(new BecomesChosenCreatureTypeTargetEffect());
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or look at the top four cards of your library, then put them back in any order.
        mode = new Mode(new LookLibraryControllerEffect(4));
        this.getSpellAbility().addMode(mode);
    }

    private TrickeryCharm(final TrickeryCharm card) {
        super(card);
    }

    @Override
    public TrickeryCharm copy() {
        return new TrickeryCharm(this);
    }
}
