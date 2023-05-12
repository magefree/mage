
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.KalitasVampireToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class KalitasBloodchiefOfGhet extends CardImpl {

    public KalitasBloodchiefOfGhet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);

        // {B}{B}{B}, {T}: Destroy target creature. If that creature dies this way, create a black Vampire creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KalitasDestroyEffect(), new ManaCostsImpl<>("{B}{B}{B}"));
        ability.addCost(new TapSourceCost());
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KalitasBloodchiefOfGhet(final KalitasBloodchiefOfGhet card) {
        super(card);
    }

    @Override
    public KalitasBloodchiefOfGhet copy() {
        return new KalitasBloodchiefOfGhet(this);
    }
}

class KalitasDestroyEffect extends OneShotEffect {

    public KalitasDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. If that creature dies this way, create a black Vampire creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness";
    }

    public KalitasDestroyEffect(final KalitasDestroyEffect effect) {
        super(effect);
    }

    @Override
    public KalitasDestroyEffect copy() {
        return new KalitasDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.destroy(source, game, false)) { // if not destroyed it returns false
            if (permanent.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(permanent.getId())
                    && game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
                // A replacement effect has moved the card to another zone as grvayard
                return true;
            }
            new CreateTokenEffect(new KalitasVampireToken(permanent.getPower().getValue(), permanent.getToughness().getValue())).apply(game, source);
        }
        return true;
    }

}
