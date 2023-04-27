
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class ZirilanOfTheClaw extends CardImpl {

    public ZirilanOfTheClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO, SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}{R}{R}, {tap}: Search your library for a Dragon permanent card and put that card onto the battlefield. Then shuffle your library.
        // That Dragon gains haste until end of turn. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZirilanOfTheClawEffect(), new ManaCostsImpl<>("{1}{R}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ZirilanOfTheClaw(final ZirilanOfTheClaw card) {
        super(card);
    }

    @Override
    public ZirilanOfTheClaw copy() {
        return new ZirilanOfTheClaw(this);
    }
}

class ZirilanOfTheClawEffect extends OneShotEffect {

    public ZirilanOfTheClawEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Search your library for a Dragon permanent card and put that card onto the battlefield. Then shuffle."
                + " That Dragon gains haste until end of turn. Exile it at the beginning of the next end step";
    }

    public ZirilanOfTheClawEffect(final ZirilanOfTheClawEffect effect) {
        super(effect);
    }

    @Override
    public ZirilanOfTheClawEffect copy() {
        return new ZirilanOfTheClawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanentCard filter = new FilterPermanentCard("a Dragon permanent card");
            filter.add(SubType.DRAGON.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        // gains haste
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                        // Exile at begin of next end step
                        ExileTargetEffect exileEffect = new ExileTargetEffect(null, null, Zone.BATTLEFIELD);
                        exileEffect.setTargetPointer(new FixedTarget(permanent, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
