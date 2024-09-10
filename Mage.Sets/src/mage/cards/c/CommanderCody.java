package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.TrooperToken;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class CommanderCody extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-token Trooper creatures");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(SubType.TROOPER.getPredicate());
    }

    public CommanderCody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TROOPER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Non-token Trooper creatures you control have "At the beginning of your upkeep, create a 1/1 white Trooper creature token."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new TrooperToken()), TargetController.YOU, false),
                Duration.WhileOnBattlefield, filter, false)
                .withForceQuotes()
        ));
    }

    private CommanderCody(final CommanderCody card) {
        super(card);
    }

    @Override
    public CommanderCody copy() {
        return new CommanderCody(this);
    }
}
