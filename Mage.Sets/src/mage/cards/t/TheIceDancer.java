package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.GoldToken;

import java.util.UUID;

/**
 * @author Grath
 */
public final class TheIceDancer extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint(
            "Artifact tokens you control", xValue
    );

    public TheIceDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ATHLETE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, {Q}: The Ice Dancer gains flying until end of turn. ({Q} is the untap symbol.)
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);

        // Whenever The Ice Dancer deals combat damage to a player, create a Gold token.
        // Then you draw X cards and gain X life, where X is the number of artifact tokens you control.
        // The Ice Dancer deals X damage to each opponent.
        // (A Gold token is an artifact with “Sacrifice this token: Add one mana of any color.”)
        ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new GoldToken())
        ).setTriggerPhrase("Whenever {this} deal combat damage to a player, ");
        ability.addEffect(new DrawCardSourceControllerEffect(xValue));
        ability.addEffect(new GainLifeEffect(xValue));
        ability.addEffect(new DamagePlayersEffect(xValue, TargetController.OPPONENT)
                .setText("{this} deals X damage to each opponent"));
        this.addAbility(ability.addHint(hint));
    }

    private TheIceDancer(final TheIceDancer card) {
        super(card);
    }

    @Override
    public TheIceDancer copy() {
        return new TheIceDancer(this);
    }
}
