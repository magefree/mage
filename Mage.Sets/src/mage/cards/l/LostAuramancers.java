package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class LostAuramancers extends CardImpl {

    public LostAuramancers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Lost Auramancers dies, if it had no time counters on it, you may search your library
        // for an enchantment card and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new LostAuramancersAbility());
    }

    private LostAuramancers(final LostAuramancers card) {
        super(card);
    }

    @Override
    public LostAuramancers copy() {
        return new LostAuramancers(this);
    }

}

class LostAuramancersAbility extends PutIntoGraveFromBattlefieldSourceTriggeredAbility {

    public LostAuramancersAbility() {
        super(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterEnchantmentCard())));
    }

    private LostAuramancersAbility(final LostAuramancersAbility ability) {
        super(ability);
    }

    @Override
    public LostAuramancersAbility copy() {
        return new LostAuramancersAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (!permanent.getCounters(game).containsKey(CounterType.TIME) || permanent.getCounters(game).getCount(CounterType.TIME) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} dies, if it had no time counters on it, you may search your library for an enchantment card, put it onto the battlefield, then shuffle.";
    }
}
