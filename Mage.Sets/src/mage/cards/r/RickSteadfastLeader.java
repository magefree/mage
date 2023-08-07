package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RickSteadfastLeader extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.HUMAN, "");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.HUMAN, "");
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 3);

    public RickSteadfastLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // As Rick enters the battlefield, choose two abilities from among first strike, vigilance, and lifelink.
        this.addAbility(new AsEntersBattlefieldAbility(new RickSteadfastLeaderChooseEffect()));

        // Humans you control have each of the chosen abilities.
        this.addAbility(new SimpleStaticAbility(new RickSteadfastLeaderGainEffect()));

        // As long as you control four or more Humans, Humans you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter),
                condition, "as long as you control four or more Humans, Humans you control get +2/+2"
        )));
    }

    private RickSteadfastLeader(final RickSteadfastLeader card) {
        super(card);
    }

    @Override
    public RickSteadfastLeader copy() {
        return new RickSteadfastLeader(this);
    }
}

class RickSteadfastLeaderChooseEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("First strike and vigilance");
        choices.add("First strike and lifelink");
        choices.add("Vigilance and lifelink");
    }

    RickSteadfastLeaderChooseEffect() {
        super(Outcome.Benefit);
        staticText = "choose two abilities from among first strike, vigilance, and lifelink";
    }

    private RickSteadfastLeaderChooseEffect(final RickSteadfastLeaderChooseEffect effect) {
        super(effect);
    }

    @Override
    public RickSteadfastLeaderChooseEffect copy() {
        return new RickSteadfastLeaderChooseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose two abilities");
        choice.setChoices(choices);
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        game.getState().setValue(source.getSourceId() + "_rick", choice.getChoice());
        return true;
    }
}

class RickSteadfastLeaderGainEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN);

    RickSteadfastLeaderGainEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "Humans you control have each of the chosen abilities";
    }

    private RickSteadfastLeaderGainEffect(final RickSteadfastLeaderGainEffect effect) {
        super(effect);
    }

    @Override
    public RickSteadfastLeaderGainEffect copy() {
        return new RickSteadfastLeaderGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object choice = game.getState().getValue(source.getSourceId() + "_rick");
        if (!(choice instanceof String)) {
            return false;
        }
        Ability ability1 = null;
        Ability ability2 = null;
        switch (((String) choice)) {
            case "First strike and vigilance":
                ability1 = FirstStrikeAbility.getInstance();
                ability2 = VigilanceAbility.getInstance();
                break;
            case "First strike and lifelink":
                ability1 = FirstStrikeAbility.getInstance();
                ability2 = LifelinkAbility.getInstance();
                break;
            case "Vigilance and lifelink":
                ability1 = VigilanceAbility.getInstance();
                ability2 = LifelinkAbility.getInstance();
                break;
            default:
                return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        )) {
            if (permanent == null) {
                continue;
            }
            permanent.addAbility(ability1, source.getSourceId(), game);
            permanent.addAbility(ability2, source.getSourceId(), game);
        }
        return true;
    }
}
