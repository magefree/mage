package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CadetToken;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class IngrisStingerquill extends CardImpl {

    public IngrisStingerquill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control attacks, that creature deals 1 damage to each opponent.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
            new IngrisStingerquillEffect(), false, true
        ));

        // {4}: Create a 2/2 colorless Wizard Soldier creature token named Cadet. Then creatures you control gain haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new CadetToken()),
            new GenericManaCost(4)
        );
        ability.addEffect(new GainAbilityControlledEffect(
            HasteAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        ).setText("Then creatures you control gain haste until end of turn"));
        this.addAbility(ability);
    }

    private IngrisStingerquill(final IngrisStingerquill card) {
        super(card);
    }

    @Override
    public IngrisStingerquill copy() {
        return new IngrisStingerquill(this);
    }
}

class IngrisStingerquillEffect extends OneShotEffect {

    public IngrisStingerquillEffect() {
        super(Outcome.Damage);
        this.staticText = "that creature deals 1 damage to each opponent";
    }

    private IngrisStingerquillEffect(final IngrisStingerquillEffect effect) {
        super(effect);
    }

    @Override
    public IngrisStingerquillEffect copy() {
        return new IngrisStingerquillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent attacker = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (attacker == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(1, attacker.getId(), source, game);
            }
        }
        return true;
    }
}
