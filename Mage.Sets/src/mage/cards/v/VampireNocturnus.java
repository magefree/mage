
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class VampireNocturnus extends CardImpl {

    public VampireNocturnus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayWithTheTopCardRevealedEffect()));
        // As long as the top card of your library is black, Vampire Nocturnus and other Vampire creatures you control get +2/+1 and have flying.
        this.addAbility(new VampireNocturnusAbility());
    }

    private VampireNocturnus(final VampireNocturnus card) {
        super(card);
    }

    @Override
    public VampireNocturnus copy() {
        return new VampireNocturnus(this);
    }
}

class VampireNocturnusAbility extends StaticAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public VampireNocturnusAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addEffect(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 1, Duration.WhileOnBattlefield),
                new VampireNocturnusCondition(), ""));
        this.addEffect(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 1, Duration.WhileOnBattlefield, filter, true),
                new VampireNocturnusCondition(), ""));
        this.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()),
                new VampireNocturnusCondition(), ""));
        this.addEffect(new ConditionalContinuousEffect(new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, true),
                new VampireNocturnusCondition(), ""));
    }

    private VampireNocturnusAbility(final VampireNocturnusAbility ability) {
        super(ability);
    }

    @Override
    public VampireNocturnusAbility copy() {
        return new VampireNocturnusAbility(this);
    }

    @Override
    public String getRule() {
        return "As long as the top card of your library is black, {this} and other Vampire creatures you control get +2/+1 and have flying.";
    }
}

class VampireNocturnusCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                return card.getColor(game).isBlack();
            }
        }
        return false;
    }
}
