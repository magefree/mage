package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.command.emblems.WillKenrithEmblem;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WillKenrith extends CardImpl {

    public WillKenrith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.WILL);
        this.setStartingLoyalty(4);

        // +2: Until your next turn, up to two target creatures each have base power and toughness 0/3 and lose all abilities.
        Ability ability = new LoyaltyAbility(
                new SetBasePowerToughnessTargetEffect(0, 3, Duration.UntilYourNextTurn)
                        .setText("until your next turn, up to two target creatures each have base power and toughness 0/3"), 2);
        ability.addEffect(new LoseAllAbilitiesTargetEffect(Duration.UntilYourNextTurn)
                .setText("and lose all abilities")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // -2: Target player draws two cards. Until your next turn, instant, sorcery, and planeswalker spells that player casts cost 2 less to cast.
        ability = new LoyaltyAbility(new DrawCardTargetEffect(2), -2);
        ability.addEffect(new WillKenrithCostReductionEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -8: Target player gets an emblem with "Whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy."
        Effect effect = new GetEmblemTargetPlayerEffect(new WillKenrithEmblem());
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Partner with Rowan Kenrith
        this.addAbility(new PartnerWithAbility("Rowan Kenrith", true, false));

        // Will Kenrith can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private WillKenrith(final WillKenrith card) {
        super(card);
    }

    @Override
    public WillKenrith copy() {
        return new WillKenrith(this);
    }
}

class WillKenrithCostReductionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    WillKenrithCostReductionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Until your next turn, instant, sorcery, and planeswalker spells that player casts cost {2} less to cast";
    }

    WillKenrithCostReductionEffect(final WillKenrithCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public WillKenrithCostReductionEffect copy() {
        return new WillKenrithCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCostReductionAllEffect effect = new SpellsCostReductionAllEffect(filter, 2);
        effect.setDuration(Duration.UntilYourNextTurn);
        effect.setControllerId(source.getFirstTarget());
        game.addEffect(effect, source);
        return true;
    }
}
