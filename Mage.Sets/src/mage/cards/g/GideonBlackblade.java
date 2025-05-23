package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GideonBlackblade extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GideonBlackblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);
        this.setStartingLoyalty(4);

        // As long as it's your turn, Gideon Blackblade is a 4/4 Human Soldier creature with indestructible that's still a planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BecomesCreatureSourceEffect(
                        new GideonBlackbladeToken(), CardType.PLANESWALKER, Duration.WhileOnBattlefield
                ), MyTurnCondition.instance, "During your turn, " +
                "{this} is a 4/4 Human Soldier creature with indestructible that's still a planeswalker."
        )));

        // Prevent all damage that would be dealt to Gideon Blackblade during your turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventAllDamageToSourceEffect(Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "Prevent all damage that would be dealt to {this} during your turn."
        )));

        // +1: Up to one other target creature you control gains your choice of vigilance, lifelink, or indestructible until end of turn.
        Ability ability = new LoyaltyAbility(new GainsChoiceOfAbilitiesEffect(
                VigilanceAbility.getInstance(), LifelinkAbility.getInstance(), IndestructibleAbility.getInstance()), 1);
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
