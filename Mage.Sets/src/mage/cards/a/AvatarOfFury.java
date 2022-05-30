package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AvatarOfFury extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or enchantment");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public AvatarOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(6, AvatarOfFuryCondition.instance)
                .setText("if an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast"))
                .addHint(new ConditionHint(AvatarOfFuryCondition.instance, "Opponent controls seven or more lands"))
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Avatar of Fury gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));
    }

    private AvatarOfFury(final AvatarOfFury card) {
        super(card);
    }

    @Override
    public AvatarOfFury copy() {
        return new AvatarOfFury(this);
    }
}

enum AvatarOfFuryCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            if (game.getBattlefield().countAll(StaticFilters.FILTER_LAND, playerId, game) > 6) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "an opponent controls seven or more lands";
    }
}
