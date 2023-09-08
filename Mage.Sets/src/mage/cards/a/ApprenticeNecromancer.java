
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class ApprenticeNecromancer extends CardImpl {

    public ApprenticeNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}, Sacrifice Apprentice Necromancer: Return target creature card from your graveyard to the battlefield. That creature gains haste. At the beginning of the next end step, sacrifice it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ApprenticeNecromancerEffect(), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ApprenticeNecromancer(final ApprenticeNecromancer card) {
        super(card);
    }

    @Override
    public ApprenticeNecromancer copy() {
        return new ApprenticeNecromancer(this);
    }
}

class ApprenticeNecromancerEffect extends OneShotEffect {

    public ApprenticeNecromancerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield. That creature gains haste. At the beginning of the next end step, sacrifice it";
    }

    private ApprenticeNecromancerEffect(final ApprenticeNecromancerEffect effect) {
        super(effect);
    }

    @Override
    public ApprenticeNecromancerEffect copy() {
        return new ApprenticeNecromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                Permanent creature = game.getPermanent(card.getId());
                if (creature != null) {
                    // Gains haste
                    ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(creature, game));
                    game.addEffect(effect, source);

                    // Sacrifice at beginning of next end step
                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice the creature", source.getControllerId());
                    sacrificeEffect.setTargetPointer(new FixedTarget(creature, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }
}
