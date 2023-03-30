package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonBlackblade extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GideonBlackblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);
        this.setStartingLoyalty(4);

        // As long as it's your turn, Gideon Blackblade is a 4/4 Human Soldier creature with indestructible that's still a planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(
                        new GideonBlackbladeToken(), "planeswalker", Duration.WhileOnBattlefield
                ), MyTurnCondition.instance, "As long as it's your turn, " +
                "{this} is a 4/4 Human Soldier creature with indestructible that's still a planeswalker."
        )).addHint(MyTurnHint.instance));

        // Prevent all damage that would be dealt to Gideon Blackblade during your turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "Prevent all damage that would be dealt to {this} during your turn."
        )));

        // +1: Up to one other target creature you control gains your choice of vigilance, lifelink, or indestructible until end of turn.
        Ability ability = new LoyaltyAbility(new GideonBlackbladeEffect(), 1);
        ability.addTarget(new TargetPermanent(0, 1, filter, false));
        this.addAbility(ability);

        // -6: Exile target nonland permanent.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -6);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private GideonBlackblade(final GideonBlackblade card) {
        super(card);
    }

    @Override
    public GideonBlackblade copy() {
        return new GideonBlackblade(this);
    }
}

class GideonBlackbladeToken extends TokenImpl {

    GideonBlackbladeToken() {
        super("", "4/4 Human Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(IndestructibleAbility.getInstance());
    }

    private GideonBlackbladeToken(final GideonBlackbladeToken token) {
        super(token);
    }

    @Override
    public GideonBlackbladeToken copy() {
        return new GideonBlackbladeToken(this);
    }
}

class GideonBlackbladeEffect extends OneShotEffect {
    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Vigilance");
        choices.add("Lifelink");
        choices.add("Indestructible");
    }

    GideonBlackbladeEffect() {
        super(Outcome.Benefit);
        staticText = "Up to one other target creature you control gains your choice of " +
                "vigilance, lifelink, or indestructible until end of turn.";
    }

    private GideonBlackbladeEffect(final GideonBlackbladeEffect effect) {
        super(effect);
    }

    @Override
    public GideonBlackbladeEffect copy() {
        return new GideonBlackbladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability to give to " + permanent.getLogName());
        choice.setChoices(choices);
        if (!player.choose(outcome, choice, game)) {
            return false;
        }
        Ability ability = null;
        switch (choice.getChoice()) {
            case "Vigilance":
                ability = VigilanceAbility.getInstance();
                break;
            case "Lifelink":
                ability = LifelinkAbility.getInstance();
                break;
            case "Indestructible":
                ability = IndestructibleAbility.getInstance();
                break;
        }
        if (ability != null) {
            game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}