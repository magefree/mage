package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceOnBattlefieldOrCommandZoneCondition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InallaArchmageRitualist extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WIZARD, "another nontoken Wizard you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.WIZARD, "untapped Wizards you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public InallaArchmageRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Eminence - Whenever another nontoken Wizard you control enters, if Inalla, Archmage Ritualist is in the command zone or on the battlefield, you may pay {1}. If you do, create a token that's a copy of that Wizard. The token gains haste. Exile it at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.ALL,
                new DoIfCostPaid(
                        new InallaArchmageRitualistEffect(), new GenericManaCost(1),
                        "Pay {1} to create a token copy?"
                ), filter, false, SetTargetPointer.PERMANENT
        ).withInterveningIf(SourceOnBattlefieldOrCommandZoneCondition.instance).setAbilityWord(AbilityWord.EMINENCE));

        // Tap five untapped Wizards you control: Target player loses 7 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(7),
                new TapTargetCost(new TargetControlledPermanent(5, filter2))
        );
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

    InallaArchmageRitualistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a token that's a copy of that Wizard. The token gains haste. Exile it at the beginning of the next end step";
    }

    private InallaArchmageRitualistEffect(final InallaArchmageRitualistEffect effect) {
        super(effect);
    }

    @Override
    public InallaArchmageRitualistEffect copy() {
        return new InallaArchmageRitualistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, null, true);
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
