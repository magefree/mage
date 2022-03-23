
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class IncandescentSoulstoke extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elemental creatures");

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
    }

    public IncandescentSoulstoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elemental creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // {1}{R}, {T}: You may put an Elemental creature card from your hand onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new IncandescentSoulstokeEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private IncandescentSoulstoke(final IncandescentSoulstoke card) {
        super(card);
    }

    @Override
    public IncandescentSoulstoke copy() {
        return new IncandescentSoulstoke(this);
    }
}

class IncandescentSoulstokeEffect extends OneShotEffect {

    private static final String choiceText = "Put an Elemental creature card from your hand onto the battlefield?";

    public IncandescentSoulstokeEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may put an Elemental creature card from your hand onto the battlefield. That creature gains haste until end of turn. Sacrifice it at the beginning of the next end step";
    }

    public IncandescentSoulstokeEffect(final IncandescentSoulstokeEffect effect) {
        super(effect);
    }

    @Override
    public IncandescentSoulstokeEffect copy() {
        return new IncandescentSoulstokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
                FilterCard filter = new FilterCreatureCard();
                filter.add(SubType.ELEMENTAL.getPredicate());
                TargetCardInHand target = new TargetCardInHand(filter);
                if (controller.choose(Outcome.PutCreatureInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addEffect(effect, source);
                                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + card.getName(), source.getControllerId());
                                sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect), source);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
