package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ProfaneCommand extends CardImpl {

    public ProfaneCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");


        DynamicValue xValue = ManacostVariableValue.REGULAR;
        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // * Target player loses X life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(xValue));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Return target creature card with converted mana cost X or less from your graveyard to the battlefield.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card with mana value X or less from your graveyard")));
        this.getSpellAbility().addMode(mode);

        // * Target creature gets -X/-X until end of turn.
        DynamicValue minusValue = new SignInversionDynamicValue(xValue);
        mode = new Mode(new BoostTargetEffect(minusValue, minusValue, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);

        // * Up to X target creatures gain fear until end of turn.
        Effect effect = new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Up to X target creatures gain fear until end of turn");
        mode = new Mode(effect);
        mode.addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addMode(mode);

        this.getSpellAbility().setTargetAdjuster(ProfaneCommandAdjuster.instance);
    }

    private ProfaneCommand(final ProfaneCommand card) {
        super(card);
    }

    @Override
    public ProfaneCommand copy() {
        return new ProfaneCommand(this);
    }
}

enum ProfaneCommandAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        // adjust targets is called for every selected mode
        Mode mode = ability.getModes().getMode();
        int xValue = ability.getManaCostsToPay().getX();
        for (Effect effect : mode.getEffects()) {
            if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                mode.getTargets().clear();
                FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue + " or less from your graveyard");
                filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
                mode.addTarget(new TargetCardInYourGraveyard(filter));
            }
            if (effect instanceof GainAbilityTargetEffect) {
                mode.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures gain fear until end of turn");
                mode.addTarget(new TargetCreaturePermanent(0, xValue, filter, false));
            }
        }
    }
}