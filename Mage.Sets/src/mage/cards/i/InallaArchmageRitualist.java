
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class InallaArchmageRitualist extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nontoken Wizard");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("untapped Wizards you control");

    static {
        filter.add(SubType.WIZARD.getPredicate());
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter2.add(SubType.WIZARD.getPredicate());
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public InallaArchmageRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Eminence - Whenever another nontoken Wizard enters the battlefield under your control, if Inalla, Archmage Ritualist is in the command zone or on the battlefield, you may pay {1}. If you do, create a token that's a copy of that Wizard. The token gains haste. Exile it at the beginning of the next end step.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(Zone.ALL, new DoIfCostPaid(
                        new InallaArchmageRitualistEffect(), new ManaCostsImpl("{1}"), "Pay {1} to create a token copy?"),
                        filter, false, SetTargetPointer.PERMANENT, ""),
                SourceOnBattlefieldOrCommandZoneCondition.instance,
                "<i>Eminence</i> &mdash; Whenever another nontoken Wizard enters the battlefield under your control, "
                + "{this} is in the command zone or on the battlefield, "
                + "you may pay {1}. If you do, create a token that's a copy of that Wizard. "
                + "That token gains haste. Exile it at the beginning of the next end step");
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);

        // Tap five untapped Wizards you control: Target player loses 7 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(7), new TapTargetCost(new TargetControlledPermanent(5, 5, filter2, true)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private InallaArchmageRitualist(final InallaArchmageRitualist card) {
        super(card);
    }

    @Override
    public InallaArchmageRitualist copy() {
        return new InallaArchmageRitualist(this);
    }
}

class InallaArchmageRitualistEffect extends OneShotEffect {

    public InallaArchmageRitualistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that Wizard. That token gains haste. Exile it at the beginning of the next end step";
    }

    public InallaArchmageRitualistEffect(final InallaArchmageRitualistEffect effect) {
        super(effect);
    }

    @Override
    public InallaArchmageRitualistEffect copy() {
        return new InallaArchmageRitualistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
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
