package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AnimateDeadTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 * @author LevelX2, awjackson
 */
public final class DanceOfTheDead extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card in a graveyard");

    public DanceOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature card in a graveyard
        TargetCardInGraveyard auraTarget = new TargetCardInGraveyard(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Dance of the Dead enters the battlefield, if it's on the battlefield, it loses "enchant creature
        // card in a graveyard" and gains "enchant creature put onto the battlefield with Dance of the Dead."
        // Put enchanted creature card onto the battlefield tapped under your control and attach Dance of the Dead
        // to it. When Dance of the Dead leaves the battlefield, that creature's controller sacrifices it.
        this.addAbility(new AnimateDeadTriggeredAbility(false, true));

        // Enchanted creature gets +1/+1 and doesn't untap during its controller's untap step.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        Effect effect = new DontUntapInControllersUntapStepEnchantedEffect();
        effect.setText("and doesn't untap during its controller's untap step");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player may pay {1}{B}. If they do, untap that creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DanceOfTheDeadDoIfCostPaidEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    private DanceOfTheDead(final DanceOfTheDead card) {
        super(card);
    }

    @Override
    public DanceOfTheDead copy() {
        return new DanceOfTheDead(this);
    }
}

class DanceOfTheDeadDoIfCostPaidEffect extends DoIfCostPaid {

    public DanceOfTheDeadDoIfCostPaidEffect() {
        super(new UntapAttachedEffect(), new ManaCostsImpl<>("{1}{B}"));
    }

    public DanceOfTheDeadDoIfCostPaidEffect(final DanceOfTheDeadDoIfCostPaidEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheDeadDoIfCostPaidEffect copy() {
        return new DanceOfTheDeadDoIfCostPaidEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (attachment != null) {
            Permanent attachedTo = game.getPermanentOrLKIBattlefield(attachment.getAttachedTo());
            if (attachedTo != null) {
                return game.getPlayer(attachedTo.getControllerId());
            }
        }
        return null;
    }

    @Override
    public String getText(Mode mode) {
        return "that player may " + CardUtil.addCostVerb(cost.getText()) + ". If they do, " + executingEffects.getText(mode);
    }
}
