
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public final class FelhideSpiritbinder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FelhideSpiritbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // <i>Inspired</i> &mdash; Whenever Felhide Spiritbinder becomes untapped, you may pay {1}{R}. If you do, create a token that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new InspiredAbility(new DoIfCostPaid(new FelhideSpiritbinderEffect(), new ManaCostsImpl<>("{1}{R}"), "Use effect of {this}?"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private FelhideSpiritbinder(final FelhideSpiritbinder card) {
        super(card);
    }

    @Override
    public FelhideSpiritbinder copy() {
        return new FelhideSpiritbinder(this);
    }
}

class FelhideSpiritbinderEffect extends OneShotEffect {

    public FelhideSpiritbinderEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step";
    }

    public FelhideSpiritbinderEffect(final FelhideSpiritbinderEffect effect) {
        super(effect);
    }

    @Override
    public FelhideSpiritbinderEffect copy() {
        return new FelhideSpiritbinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, CardType.ENCHANTMENT, true);
            effect.setTargetPointer(getTargetPointer());
            if (effect.apply(game, source)) {
                for (Permanent tokenPermanent : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }

        return false;
    }
}
