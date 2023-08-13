package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Helldozer extends CardImpl {

    public Helldozer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // {B}{B}{B}, {tap}: Destroy target land. If that land was nonbasic, untap Helldozer.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new HelldozerEffect(),
                new ManaCostsImpl<>("{B}{B}{B}"));
        ability.addTarget(new TargetLandPermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private Helldozer(final Helldozer card) {
        super(card);
    }

    @Override
    public Helldozer copy() {
        return new Helldozer(this);
    }
}

class HelldozerEffect extends OneShotEffect {

    public HelldozerEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target land. If that land was nonbasic, untap Helldozer";
    }

    public HelldozerEffect(final HelldozerEffect effect) {
        super(effect);
    }

    @Override
    public HelldozerEffect copy() {
        return new HelldozerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent helldozer = game.getPermanent(source.getSourceId());
        Permanent landTarget = game.getPermanent(source.getFirstTarget());
        if (landTarget != null) {
            boolean wasNonBasic = !landTarget.isBasic(game);
            landTarget.destroy(source, game, false);
            if (wasNonBasic
                    && helldozer != null) {
                return helldozer.untap(game);
            }
        }
        return false;
    }
}
