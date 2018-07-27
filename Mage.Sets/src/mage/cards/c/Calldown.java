package mage.cards.c;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TerranSoldierToken;
import mage.target.TargetPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Calldown extends CardImpl {

    public Calldown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");
        

        // Choose one -
        //   Untap two target permanents.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, StaticFilters.FILTER_PERMANENT));

        //   Put a 2/2 red and white Terran Soldier creature token onto the battlefield.
        Mode mode = new Mode();
        mode.getEffects().add(new CreateTokenEffect(new TerranSoldierToken()));
        this.getSpellAbility().addMode(mode);

        //   Add {R}{R}{W}{W} to your mana pool.
        mode = new Mode();
        mode.getEffects().add(new AddManaToManaPoolSourceControllerEffect(new Mana(2, 0, 0, 2, 0, 0, 0, 0)));
        this.getSpellAbility().addMode(mode);
    }

    public Calldown(final Calldown card) {
        super(card);
    }

    @Override
    public Calldown copy() {
        return new Calldown(this);
    }
}
