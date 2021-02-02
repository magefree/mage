package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GildedCerodon extends CardImpl {

    private static final String rule = "Whenever {this} attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.";

    public GildedCerodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Gilded Cerodon attacks, if you control a Desert or there is a Desert card in your graveyard, target creature can't block this turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false), new GildedCerodonCondition(), rule);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private GildedCerodon(final GildedCerodon card) {
        super(card);
    }

    @Override
    public GildedCerodon copy() {
        return new GildedCerodon(this);
    }
}

class GildedCerodonCondition implements Condition {

    private static final FilterPermanent filter = new FilterPermanent();
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter.add(SubType.DESERT.getPredicate());
        filter2.add(SubType.DESERT.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        return (!game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game).isEmpty()
                || !controller.getGraveyard().getCards(filter2, game).isEmpty());
    }

    @Override
    public String toString() {
        return "if you control a Desert or there is a Desert card in your graveyard";
    }

}
