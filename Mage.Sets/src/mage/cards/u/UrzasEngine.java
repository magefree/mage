package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class UrzasEngine extends CardImpl {

    public UrzasEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {3}: Urza's Engine gains banding until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(BandingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{3}")));

        // {3}: Attacking creatures banded with Urza's Engine gain trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasEngineEffect(), new ManaCostsImpl("{3}")));
    }

    public UrzasEngine(final UrzasEngine card) {
        super(card);
    }

    @Override
    public UrzasEngine copy() {
        return new UrzasEngine(this);
    }

}

class UrzasEngineEffect extends OneShotEffect {

    public UrzasEngineEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Attacking creatures banded with {this} gain trample until end of turn";
    }

    public UrzasEngineEffect(final UrzasEngineEffect effect) {
        super(effect);
    }

    @Override
    public UrzasEngineEffect copy() {
        return new UrzasEngineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            for (UUID bandedId : sourcePermanent.getBandedCards()) {
                Permanent banded = game.getPermanent(bandedId);
                if (banded != null
                        && banded.isAttacking()
                        && banded.getBandedCards() != null
                        && banded.getBandedCards().contains(sourcePermanent.getId())) {
                    GainAbilityTargetEffect effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(bandedId, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
