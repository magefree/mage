package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author NinthWorld
 */
public final class Baneling extends CardImpl {

    public Baneling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {1}{B}: Baneling gets +2/+2 until end of turn. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl("{1}{B}"));
        ability.addEffect(new BanelingSacrificeEffect());
        this.addAbility(ability);
    }

    public Baneling(final Baneling card) {
        super(card);
    }

    @Override
    public Baneling copy() {
        return new Baneling(this);
    }
}

class BanelingSacrificeEffect extends OneShotEffect {

    public BanelingSacrificeEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice it at the beginning of the next end step";
    }

    public BanelingSacrificeEffect(final BanelingSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public BanelingSacrificeEffect copy() {
        return new BanelingSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice {this}");
            sacrificeEffect.setTargetPointer(new FixedTarget(sourcePermanent.getId()));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
        }
        return false;
    }
}