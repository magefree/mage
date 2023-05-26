
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.EnchantedSourceCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.permanent.AttachmentAttachedToCardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KitsuneMystic extends CardImpl {

    public KitsuneMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.flipCard = true;
        this.flipCardName = "Autumn-Tail, Kitsune Sage";

        // At the beginning of the end step, if Kitsune Mystic is enchanted by two or more Auras, flip it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect(new AutumnTailKitsuneSage())),
                new EnchantedSourceCondition(2), "At the beginning of the end step, if {this} is enchanted by two or more Auras, flip it."));
    }

    private KitsuneMystic(final KitsuneMystic card) {
        super(card);
    }

    @Override
    public KitsuneMystic copy() {
        return new KitsuneMystic(this);
    }
}

class AutumnTailKitsuneSage extends TokenImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a creature");

    static {
        filter.add(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE));
        filter.add(SubType.AURA.getPredicate());
    }

    AutumnTailKitsuneSage() {
        super("Autumn-Tail, Kitsune Sage", "");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.FOX);
        subtype.add(SubType.WIZARD);
        power = new MageInt(4);
        toughness = new MageInt(5);

        // {1}: Attach target Aura attached to a creature to another creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AutumnTailEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
    public AutumnTailKitsuneSage(final AutumnTailKitsuneSage token) {
        super(token);
    }

    public AutumnTailKitsuneSage copy() {
        return new AutumnTailKitsuneSage(this);
    }
}

class AutumnTailEffect extends OneShotEffect {

    public AutumnTailEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura attached to a creature to another creature";
    }

    public AutumnTailEffect(final AutumnTailEffect effect) {
        super(effect);
    }

    @Override
    public AutumnTailEffect copy() {
        return new AutumnTailEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (aura != null && creature != null) {
            Permanent oldCreature = game.getPermanent(aura.getAttachedTo());
            if (oldCreature == null || oldCreature.equals(creature)) {
                return false;
            }
            if (oldCreature.removeAttachment(aura.getId(), source, game)) {
                return creature.addAttachment(aura.getId(), source, game);
            }
        }
        return false;
    }
}
