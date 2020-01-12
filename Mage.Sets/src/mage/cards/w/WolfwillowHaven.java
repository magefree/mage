package mage.cards.w;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.TriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolfwillowHaven extends CardImpl {

    public WolfwillowHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds an additional {G}.
        this.addAbility(new WolfwillowHavenTriggeredAbility());

        // {4}{G}, Sacrifice Wolfwillow Haven: Create a 2/2 green Wolf creature token. Activate this ability only during your turn.
        ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()),
                new ManaCostsImpl("{4}{G}"), MyTurnCondition.instance
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private WolfwillowHaven(final WolfwillowHaven card) {
        super(card);
    }

    @Override
    public WolfwillowHaven copy() {
        return new WolfwillowHaven(this);
    }
}

class WolfwillowHavenTriggeredAbility extends TriggeredManaAbility {

    WolfwillowHavenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.G), "their"));
    }

    private WolfwillowHavenTriggeredAbility(final WolfwillowHavenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WolfwillowHavenTriggeredAbility copy() {
        return new WolfwillowHavenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment == null || !event.getSourceId().equals(enchantment.getAttachedTo())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
        return true;
    }


    @Override
    public String getRule() {
        return "Whenever enchanted land is tapped for mana, its controller adds an additional {G}";
    }
}
