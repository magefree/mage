package mage.cards.o;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author weirddan455
 */
public final class OrcusPrinceOfUndeath extends CardImpl {

    public OrcusPrinceOfUndeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Orcus, Prince of Undeath enters the battlefield, choose one —
        // • Each other creature gets -X/-X until end of turn. You lose X life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new OrcusPrinceOfUndeathBoostEffect());

        // • Return up to X target creature cards with total mana value X or less from your graveyard to the battlefield. They gain haste until end of turn.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect().setText("Return up to X target creature cards with total mana value X or less from your graveyard to the battlefield"));
        mode.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, "They gain haste until end of turn"));
        ability.addMode(mode);
        ability.setTargetAdjuster(OrcusPrinceOfUndeathAdjuster.instance);
        this.addAbility(ability);
    }

    private OrcusPrinceOfUndeath(final OrcusPrinceOfUndeath card) {
        super(card);
    }

    @Override
    public OrcusPrinceOfUndeath copy() {
        return new OrcusPrinceOfUndeath(this);
    }
}

class OrcusPrinceOfUndeathBoostEffect extends OneShotEffect {

    public OrcusPrinceOfUndeathBoostEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Each other creature gets -X/-X until end of turn. You lose X life";
    }

    private OrcusPrinceOfUndeathBoostEffect(final OrcusPrinceOfUndeathBoostEffect effect) {
        super(effect);
    }

    @Override
    public OrcusPrinceOfUndeathBoostEffect copy() {
        return new OrcusPrinceOfUndeathBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = ManacostVariableValue.ETB.calculate(game, source, this);
        game.addEffect(new BoostAllEffect(-xValue, -xValue, Duration.EndOfTurn, true), source);
        controller.loseLife(xValue, game, source, false);
        return true;
    }
}

class OrcusPrinceOfUndeathTarget extends TargetCardInYourGraveyard {

    private final int xValue;

    public OrcusPrinceOfUndeathTarget(int xValue, FilterCreatureCard filter) {
        super(0, xValue, filter);
        this.xValue = xValue;
    }

    private OrcusPrinceOfUndeathTarget(final OrcusPrinceOfUndeathTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public OrcusPrinceOfUndeathTarget copy() {
        return new OrcusPrinceOfUndeathTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        int maxManaValue = this.xValue;
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                maxManaValue -= card.getManaValue();
            }
        }
        for (UUID possibleTargetId : super.possibleTargets(sourceControllerId, source, game)) {
            Card card = game.getCard(possibleTargetId);
            if (card != null && card.getManaValue() <= maxManaValue) {
                possibleTargets.add(possibleTargetId);
            }
        }
        return possibleTargets;
    }
}

enum OrcusPrinceOfUndeathAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ManacostVariableValue.ETB.calculate(game, ability, null);
        FilterCreatureCard filter = new FilterCreatureCard("creature cards with total mana value " + xValue + " or less from your graveyard");
        for (Mode mode : ability.getModes().values()) {
            boolean setTarget = false;
            for (Effect effect : mode.getEffects()) {
                if (effect instanceof ReturnFromGraveyardToBattlefieldTargetEffect) {
                    setTarget = true;
                    break;
                }
            }
            if (setTarget) {
                mode.getTargets().clear();
                mode.addTarget(new OrcusPrinceOfUndeathTarget(xValue, filter));
            }
        }
    }
}
