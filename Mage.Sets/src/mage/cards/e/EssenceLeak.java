package mage.cards.e;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.costs.Cost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author noahg
 */
public final class EssenceLeak extends CardImpl {

    public EssenceLeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As long as enchanted permanent is red or green, it has "At the beginning of your upkeep, sacrifice this permanent unless you pay its mana cost."
        Ability sacAbility =new BeginningOfUpkeepTriggeredAbility(new EssenceLeakEffect(), TargetController.YOU, false);
        ContinuousEffect isRedOrGreenAbility = new GainAbilityAttachedEffect(sacAbility, AttachmentType.AURA);
        SimpleStaticAbility ifRedOrGreenAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(isRedOrGreenAbility,
                new OrCondition(new EnchantedCreatureColorCondition(ObjectColor.RED), new EnchantedCreatureColorCondition(ObjectColor.GREEN)),
                "As long as enchanted permanent is red or green, it has \"At the beginning of your upkeep, sacrifice this permanent unless you pay its mana cost.\""));
        this.addAbility(ifRedOrGreenAbility);
    }

    private EssenceLeak(final EssenceLeak card) {
        super(card);
    }

    @Override
    public EssenceLeak copy() {
        return new EssenceLeak(this);
    }
}

class EssenceLeakEffect extends OneShotEffect {

    public EssenceLeakEffect() {
        super(Outcome.Sacrifice);
        staticText =  "sacrifice this permanent unless you pay its mana cost";
    }

    public EssenceLeakEffect(final EssenceLeakEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && permanent != null) {

            String message = CardUtil.replaceSourceName("Pay {this}'s mana cost?", permanent.getLogName());
            Cost cost = permanent.getManaCost().copy();
            if (player.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            permanent.sacrifice(source, game);
            return true;
        }
        return false;
    }

    @Override
    public EssenceLeakEffect copy() {
        return new EssenceLeakEffect(this);
    }

}
