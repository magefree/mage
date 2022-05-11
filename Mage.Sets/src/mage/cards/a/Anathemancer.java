package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class Anathemancer extends CardImpl {

    public Anathemancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Anathemancer enters the battlefield, it deals damage to target player equal to the number of nonbasic lands that player controls.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(AnathemancerCount.instance, "it")
                        .setText("it deals damage to target player equal to the number of nonbasic lands that player controls")
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Unearth {5}{B}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{5}{B}{R}")));
    }

    private Anathemancer(final Anathemancer card) {
        super(card);
    }

    @Override
    public Anathemancer copy() {
        return new Anathemancer(this);
    }
}

enum AnathemancerCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility.getFirstTarget() == null) {
            return 0;
        }
        return game.getBattlefield().count(
                StaticFilters.FILTER_LANDS_NONBASIC,
                sourceAbility.getControllerId(),
                sourceAbility, game
        );
    }

    @Override
    public AnathemancerCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String getMessage() {
        return "nonbasic lands that player controls";
    }
}
