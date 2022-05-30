package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BalduvianToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class BalduvianDead extends CardImpl {

    public BalduvianDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {2}{R}, Exile a creature card from your graveyard: Create a 3/1 black and red Graveborn creature token with haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BalduvianDeadEffect(), new ManaCostsImpl<>("{2}{R}"));
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        ability.addCost(new ExileFromGraveCost(target));
        this.addAbility(ability);

    }

    private BalduvianDead(final BalduvianDead card) {
        super(card);
    }

    @Override
    public BalduvianDead copy() {
        return new BalduvianDead(this);
    }
}

class BalduvianDeadEffect extends OneShotEffect {

    public BalduvianDeadEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 3/1 black and red Graveborn creature token with haste. Sacrifice it at the beginning of the next end step";
    }

    public BalduvianDeadEffect(final BalduvianDeadEffect effect) {
        super(effect);
    }

    @Override
    public BalduvianDeadEffect copy() {
        return new BalduvianDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new BalduvianToken());
        effect.apply(game, source);
        for (UUID tokenId : effect.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice the token at the beginning of the next end step", source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
        }
        return true;
    }
}
