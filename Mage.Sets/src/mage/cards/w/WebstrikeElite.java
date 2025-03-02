package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WebstrikeElite extends CardImpl {

    public WebstrikeElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Cycling {X}{G}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{X}{G}{G}")));

        // When you cycle this card, destroy up to one target artifact or enchantment with mana value X.
        this.addAbility(new WebstrikeEliteTriggeredAbility());
    }

    private WebstrikeElite(final WebstrikeElite card) {
        super(card);
    }

    @Override
    public WebstrikeElite copy() {
        return new WebstrikeElite(this);
    }
}

class WebstrikeEliteTriggeredAbility extends ZoneChangeTriggeredAbility {

    WebstrikeEliteTriggeredAbility() {
        super(Zone.ALL, new DestroyTargetEffect(), "", false);
    }

    private WebstrikeEliteTriggeredAbility(final WebstrikeEliteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public WebstrikeEliteTriggeredAbility copy() {
        return new WebstrikeEliteTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object == null || !(object.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with mana value X");
        filter.add(new ManaValuePredicate(
                ComparisonType.EQUAL_TO, GetXValue.instance.calculate(game, object.getStackAbility(), null)
        ));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(0, 1, filter));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cycle this card, destroy up to one target artifact or enchantment with mana value X.";
    }
}
