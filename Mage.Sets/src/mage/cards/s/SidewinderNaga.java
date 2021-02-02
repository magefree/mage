
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class SidewinderNaga extends CardImpl {

    public SidewinderNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as  you control a Desert or there is a Desert card in your graveyard, Sidewinder Naga gets +1/+0 and has trample.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield), new DesertInGYorBFCondition(), "As long as  you control a Desert or there is a Desert card in your graveyard, {this} gets +1/+0 ");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield), new DesertInGYorBFCondition(), "and has trample"));
        this.addAbility(ability);
    }

    private SidewinderNaga(final SidewinderNaga card) {
        super(card);
    }

    @Override
    public SidewinderNaga copy() {
        return new SidewinderNaga(this);
    }
}

class DesertInGYorBFCondition implements Condition {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    private static final FilterControlledLandPermanent filter2 = new FilterControlledLandPermanent("a desert");

    static {
        filter2.add(SubType.DESERT.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    return true;
                }
            }
        }

        PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter2);
        return count.calculate(game, source, null) >= 1;
    }
}
